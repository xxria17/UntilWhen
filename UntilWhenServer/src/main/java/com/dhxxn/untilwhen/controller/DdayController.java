package com.dhxxn.untilwhen.controller;

import com.dhxxn.untilwhen.model.Dday;
import com.dhxxn.untilwhen.model.User;
import com.dhxxn.untilwhen.repository.UserRepository;
import com.dhxxn.untilwhen.service.DdayService;
import com.dhxxn.untilwhen.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/dday")
public class DdayController {

    private final DdayService ddayService;
    private final UserRepository userService;

    @Autowired
    public DdayController(DdayService ddayService, UserRepository userService) {
        this.ddayService = ddayService;
        this.userService = userService;
    }


    // 작성한 글 조회
    @GetMapping("/")
    public List<Dday> getAllUsers(Principal principal) {
        return ddayService.findAllByUserName(principal.getName());
    }

    @GetMapping("/{id}")
    public Dday get(@PathVariable Integer id) {
        Dday dday = ddayService.findById(id);
        return dday;
    }

    // 게시글 생성
    @PostMapping("/")
    public Dday addDday(Principal principal, @RequestBody Dday dday) {
        User user = userService.findByName(principal.getName()).get();
        dday.setUser(user);
        return ddayService.save(dday);
    }

    //게시글 삭제
    @DeleteMapping("/{id}")
    public void deleteDday(@PathVariable Integer id) {
        ddayService.findById(id).setUser(null);
        ddayService.delete(id);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public Dday updateDday(@PathVariable Integer id, @RequestBody Dday updates) {
        Dday dday = ddayService.findById(id);
        dday.setContent(updates.getContent());
        dday.setStartDate(updates.getStartDate());
        dday.setFinishDate(updates.getFinishDate());
        return ddayService.save(dday);
    }
}
