package com.medbis.service.impl;

import com.medbis.entity.Visit;
import com.medbis.repository.VisitRepository;
import com.medbis.service.interfaces.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisitServiceImpl implements VisitService {

    private VisitRepository visitRepository;

    @Autowired
    public VisitServiceImpl(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public List<Visit> findAll() {
        return visitRepository.findAll();
    }

    @Override
    public Visit findById(int theId) {
        Optional<Visit> visit =  this.visitRepository.findById(theId);
        if(!visit.isPresent()){
            throw new RuntimeException("item with" + theId + "not found");
        }
        return visit.get();
    }

    @Override
    public void save(Visit visit) {
        this.visitRepository.save(visit);
    }

    @Override
    public void deleteById(int theId) {
        this.visitRepository.deleteById(theId);
    }




    @Override
    public List<Visit> findPlannedVisits(){ return visitRepository.findByVisitStatusIsFalse();}

    @Override
    public List<Visit> findAccomplishedVisits() {
        return visitRepository.findByVisitStatusIsTrue();
    }


    private boolean isNewVisitAdded(int initialAmountOfPlannedVisit){
        int actualAmountOfPlannedVisit = this.visitRepository.findByVisitStatusIsFalse().size();
        return initialAmountOfPlannedVisit + 1 == actualAmountOfPlannedVisit;
    }

    @Override
    public List<Visit> findAllByEmployeeId(int id) {
        return visitRepository.findByVisitEmployeeId(id);
    }

    @Override
    public List<Visit> findAccomplishedVisitsByEmployeeId(int id) {
        return visitRepository.findByVisitStatusIsTrueAndEmployeeId(id);
    }

    @Override
    public List<Visit> findPlannedVisitsByEmployeeId(int id) {
        return visitRepository.findByVisitStatusIsFalseAndEmployeeId(id);
    }

    public String setCorrectAction(int initialAmountOfPlannedVisit){
        if(isNewVisitAdded(initialAmountOfPlannedVisit)) return "addVisit";
        else return "editVisit";
    }

    @Override
    public List<Visit> findAllByVisitPatientIdOrderByVisitDateDesc(int id) {
        return visitRepository.findAllByVisitPatientIdOrderByVisitDateDesc(id);
    }


}
