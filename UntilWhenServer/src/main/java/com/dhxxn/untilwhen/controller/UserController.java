package com.dhxxn.untilwhen.controller;

import com.dhxxn.untilwhen.model.Dday;
import com.dhxxn.untilwhen.model.User;
import com.dhxxn.untilwhen.service.DdayService;
import com.dhxxn.untilwhen.repository.UserRepository;
import com.dhxxn.untilwhen.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {

    private final DdayService ddayService;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(DdayService ddayService, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.ddayService = ddayService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 모든 회원 정보 조회
    @GetMapping("/")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // 이름으로 회원 정보 조회
    @GetMapping("/{name}")
    public User findByName(@PathVariable String name) {
        return userRepository.findByName(name).get();
    }

    // 회원가입
    @PostMapping("/")
    public User signUp(@RequestBody Map<String, String> newUser) {
        return userRepository.save(
                User.builder().name(newUser.get("name"))
                .password(passwordEncoder.encode(newUser.get("password")))
                .admin(Collections.singletonList("ROLE_USER"))
                .build()
        );
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> inputUser) {
        User user = userRepository.findByName(inputUser.get("name"))
                .orElseThrow(() -> new IllegalArgumentException());

        if (!passwordEncoder.matches(inputUser.get("password"), user.getPassword())) {
            throw new IllegalStateException();
        }
        return jwtTokenProvider.createToken(user.getUsername(), user.getAdmin());
    }

    // 회원 탈퇴
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        List<Dday> list = ddayService.findAllByUserName(userRepository.findById(id).get().getName());
        list.forEach(element -> {
            element.setUser(null);
            ddayService.delete(element.getId());
        });
        userRepository.deleteById(id);
    }

    //에러 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Integer> nullUser() {
        return new ResponseEntity<>(100, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Integer> wrongPw() {
        return new ResponseEntity<>(200, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Integer> existingUser() {
        return new ResponseEntity<>(300, HttpStatus.NOT_FOUND);
    }

    /*
    가입되지 않은 사용자 :: 100
    잘못된 비밀번호 :: 200
    이미 있는 유저 :: 300
     */
}
