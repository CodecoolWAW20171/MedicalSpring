package com.medbis.service.impl;

import com.medbis.entity.Medicine;
import com.medbis.repository.MedicineRepository;
import com.medbis.service.interfaces.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineServiceImpl implements MedicineService {

    private MedicineRepository medicineRepository;

    @Autowired
    public MedicineServiceImpl(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    @Override
    public List<Medicine> findAll() {
        return medicineRepository.findAll();
    }

    @Override
    public Medicine findById(int theId) {
        Optional<Medicine> result = medicineRepository.findById(theId);
        Medicine theMedicine = null;
        if(result.isPresent()){
            theMedicine = result.get();
        }else{
            throw new RuntimeException("Dod not find medicine id" + theId);
        }
        return theMedicine;
    }

    @Override
    public void save(Medicine theMedicine) {
        medicineRepository.save(theMedicine);
    }

    @Override
    public void deleteById(int theId) {
        medicineRepository.deleteById(theId);
    }
}
