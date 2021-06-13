package com.dhxxn.untilwhen.service;

import com.dhxxn.untilwhen.model.Dday;
import com.dhxxn.untilwhen.repository.DdayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        return ddayRepository.save(calculateDays(dday));
    }

    public void delete(Integer id) {
        ddayRepository.deleteById(id);
    }

    private Dday calculateDays(Dday dday) {
        Date startDate = dday.getStartDate();
        Date finishDate = dday.getFinishDate();
        Date currentDate = new Date();

        long calTotal = startDate.getTime() - finishDate.getTime();
        long calCurrent = currentDate.getTime() - finishDate.getTime();
        long cal = 24*60*60*1000;

        dday.setTotalRemainDates((calTotal / cal)+1);
        dday.setCurrentRemainDates((calCurrent / cal)+1);

        return dday;
    }
}
