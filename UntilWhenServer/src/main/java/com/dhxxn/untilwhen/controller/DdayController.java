package com.dhxxn.untilwhen.controller;

import com.dhxxn.untilwhen.model.Dday;
import com.dhxxn.untilwhen.service.DdayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/dday")
public class DdayController {

    private final DdayService ddayService;

    @Autowired
    public DdayController(DdayService ddayService) {
        this.ddayService = ddayService;
    }

    // 작성한 글 조회
    @GetMapping("/")
    public List<Dday> getAllUsers(@PathVariable String name) {
        return ddayService.findAllByUserName(name);
    }

    @GetMapping("/{id}")
    public Dday get(@PathVariable Integer id) {
        Dday dday = ddayService.findById(id);
        return dday;
    }

    // 게시글 생성
    @PostMapping("/")
    public Dday addDday(@ModelAttribute Dday dday) {
        return ddayService.save(dday);
    }

    //게시글 삭제
    @DeleteMapping("/{id}")
    public void deleteDday(@PathVariable Integer id) {
        ddayService.delete(ddayService.findById(id));
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public Dday updateDday(@PathVariable Integer id, @ModelAttribute final Dday updates) {
        Dday dday = ddayService.findById(id);
        dday.setContent(updates.getContent());
        dday.setStartDate(updates.getStartDate());
        dday.setFinishDate(updates.getFinishDate());
        return ddayService.save(dday);
    }
}
