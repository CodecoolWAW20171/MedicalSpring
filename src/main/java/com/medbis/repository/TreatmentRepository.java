package com.medbis.repository;

import com.medbis.entity.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TreatmentRepository extends JpaRepository<Treatment, Integer> {


    List<Treatment> findAllByCategoryId(int categoryId);
/*
    move to analysis package
*/
  @Query(value = "SELECT count(services.service_id) from services\n" +
                    "join visits_services vs on services.service_id = vs.service_id\n" +
                    "join visits on vs.visit_id = visits.visit_id\n" +
                    "join employees e on visits.employee_id = e.employee_id WHERE e.employee_id = :id \n" +
                    "and visits.status is TRUE "  +
                    "GROUP BY e.employee_id",
            nativeQuery = true)
  Integer countByEmployee(@Param("id") int id);

@Query(value = "SELECT count(services.service_id), e.employee_id, e.name, e.surname from services\n" +
        "join visits_services vs on services.service_id = vs.service_id\n" +
        "join visits on vs.visit_id = visits.visit_id\n" +
        "join employees e on visits.employee_id = e.employee_id\n"  +
        "WHERE visits.status IS true\n" +
        "and e.employee_id = :id \n" +
        "and visits.date BETWEEN :firstDayOfMonthDate and :lastDayOfMonthDate \n" +
        "GROUP BY e.employee_id", nativeQuery = true)
Integer countByEmployeeByMonth(@Param("id") int id, LocalDate firstDayOfMonthDate, LocalDate lastDayOfMonthDate);


@Query(value = "SELECT services.service_id, count(services.service_id) FROM services " +
        "join visits_services  on services.service_id = visits_services.service_id " +
        "group by services.service_id", nativeQuery = true)
    Map<Integer, Integer> getAmountSingleTreatmentDoneEmployee();






}