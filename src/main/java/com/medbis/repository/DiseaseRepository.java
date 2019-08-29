package com.medbis.repository;

import com.medbis.entity.Disease;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiseaseRepository extends JpaRepository<Disease, Integer> {

}
