package com.medbis.repository;

import com.medbis.entity.VisitTreatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;


public interface VisitTreatmentRepository extends JpaRepository<VisitTreatment, Integer> {

    List<VisitTreatment> findAll();


    @Query(value = "SELECT DISTINCT * FROM visits_services ", nativeQuery = true)
    List<VisitTreatment> retriveVisitTreatmentDistinctly();

    @Query(value = "SELECT count(visit_id) from visits_services\n" +
            "JOIN services s on visits_services.service_id = s.service_id\n" +
            "WHERE s.service_id = :id", nativeQuery = true)
    int totalService(int id);


    @Query(value = "SELECT * FROM visits_services vs\n" +
            "JOIN visits v on vs.visit_id = v.visit_id\n" +
            "where v.status is TRUE\n" +
            "and v.date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<VisitTreatment> takeVisitTreatmentsDoneInMonth(LocalDate startDate, LocalDate endDate);


    @Query(value = "SELECT count(s.service_id) FROM visits_services vs\n" +
            "JOIN visits v on vs.visit_id = v.visit_id\n" +
            "JOIN services s on s.service_id = vs.service_id\n" +
            "where v.date >= :startDate\n" +
            "AND v.date < :endDate\n" +
            "AND s.service_id = :id", nativeQuery = true)
    int takeSumOfOneTreatmentDoneInMonth(int id, LocalDate startDate, LocalDate endDate);






}
