package com.medbis.security;

import com.medbis.controller.HomeController;
import com.medbis.entity.Employee;
import com.medbis.repository.EmployeeRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserPrinicipalDetailsService implements UserDetailsService {

     private EmployeeRepository employeeRepository;
     @Lazy
     private HomeController homeController;

     public UserPrinicipalDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
     }

    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            Employee employee = this.employeeRepository.findByLogin(username);
            return new UserPrincipal(employee);
        }
        catch (NullPointerException err){
            System.out.println("NullPointerException in UserPrincipalDetailservice.loadByUserName");
          homeController.showLoginForm();
        }
        return null;
    }
}
