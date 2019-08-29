package com.medbis.service.impl;

import com.medbis.entity.Employee;
import com.medbis.entity.User;
import com.medbis.repository.EmployeeRepository;
import com.medbis.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service(value = "EmployeeServiceImpl")
public class EmployeeServiceImpl implements UserService{

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<? extends User> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public User findById(int id) {
        Optional<Employee> result = employeeRepository.findById(id);
        Employee employee;
        if(result.isPresent()){
            employee = result.get();
        }else{
            throw new RuntimeException("Dod not find patient id" + id);
        }
        return employee;
    }

    @Override
    public void save(User user) {
        employeeRepository.save((Employee) user);
    }

    @Override
    public void deleteById(int id) {
        employeeRepository.deleteById(id);
    }



    @Override
    public User findByName(String name) {
        try {
            return employeeRepository.findByName(name);
        } catch (NullPointerException err) {
            err.printStackTrace();
        }

        return null;
    }

}
