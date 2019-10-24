package com.medbis.repository;

import com.medbis.entity.VisitTreatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface VisitTreatmentRepository extends JpaRepository<VisitTreatment, Integer> {

    List<VisitTreatment> findAll();


    @Query(value = "SELECT DISTINCT * FROM visits_services ", nativeQuery = true)
    List<VisitTreatment> retriveVisitTreatmentDistinctly();
    /*
    metoda do wyrzucenia(?)
?    */
    @Query(value = "SELECT count(visit_id) from visits_services\n" +
            "JOIN services s on visits_services.service_id = s.service_id\n" +
            "WHERE s.service_id = :id", nativeQuery = true)
    int totalService(int id);







}
