package com.medbis.service.interfaces;

import com.medbis.entity.Visit;

import java.util.List;

public interface VisitService {

    List<Visit> findAll();

    Visit findById(int theId);

    void save(Visit theMedicine);

    void deleteById(int theId);

    List<Visit> findPlannedVisits();

    List<Visit> findAccomplishedVisits();

    List<Visit> findAllByEmployeeId(int id);

    List<Visit> findAccomplishedVisitsByEmployeeId(int id);

    List<Visit> findPlannedVisitsByEmployeeId(int id);

    String setCorrectAction(int isNewVisitAdded);

    List<Visit> findAllByVisitPatientIdOrderByVisitDateDesc(int id);
}


