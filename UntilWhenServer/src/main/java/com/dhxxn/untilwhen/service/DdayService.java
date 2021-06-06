package com.dhxxn.untilwhen.service;

import com.dhxxn.untilwhen.model.Dday;
import com.dhxxn.untilwhen.repository.DdayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DdayService {
    private final DdayRepository ddayRepository;

    @Autowired
    public DdayService(DdayRepository ddayRepository) {
        this.ddayRepository = ddayRepository;
    }

    public List<Dday> findAll() {
        return ddayRepository.findAll();
    }

    public List<Dday> findAllByUserName(String name) {
        return ddayRepository.findAllByUserName(name);
    }

    public Dday findById(Integer id) {
        return ddayRepository.findById(id).get();
    }

    public Dday save(Dday dday) {
        return ddayRepository.save(dday);
    }

    public void delete(Dday dday) {
        ddayRepository.delete(dday);
    }
}
