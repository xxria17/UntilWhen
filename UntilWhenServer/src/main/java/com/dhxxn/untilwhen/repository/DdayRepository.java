package com.dhxxn.untilwhen.repository;

import com.dhxxn.untilwhen.model.Dday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DdayRepository extends JpaRepository<Dday, Integer> {
    List<Dday> findAllByUserName(String name);
}
