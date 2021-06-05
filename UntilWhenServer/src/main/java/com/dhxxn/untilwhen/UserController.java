package com.dhxxn.untilwhen;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 모든 회원 정보 조회
    @GetMapping("/")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // 아이디값에 해당하는 회원 정보 조회
    @GetMapping("/{id}")
    public Optional<User> findById(@PathVariable Integer id) {
        return userRepository.findById(id);
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
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 회원입니다"));

        if (!passwordEncoder.matches(inputUser.get("password"), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }
        return jwtTokenProvider.createToken(user.getUsername(), user.getAdmin());
    }

    // 회원 탈퇴
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        userRepository.delete(userRepository.findById(id).get());
    }
}
