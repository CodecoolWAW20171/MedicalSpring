package com.medbis.repository;

import com.medbis.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Integer> {


    List<Visit> findAll();

    List<Visit> findByVisitStatusIsFalse();

    List<Visit> findByVisitStatusIsTrue();


    int countVisitsByVisitDateBetweenAndVisitStatusAndEmployeeId(LocalDate startDate, LocalDate endDate, Boolean status, int employeeId);


    int countVisitsByEmployeeIdAndVisitStatus(int id, boolean visitStatus);


    List<Visit> findByVisitEmployeeId(int id);


    List<Visit> findByVisitStatusIsTrueAndEmployeeId(int id);

    List<Visit> findByVisitStatusIsFalseAndEmployeeId(int id);


    int countVisitsByVisitStatus(boolean visitStatus);



}
