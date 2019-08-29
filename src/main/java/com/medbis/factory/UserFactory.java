package com.medbis.factory;

import com.medbis.entity.Employee;
import com.medbis.entity.Patient;
import com.medbis.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {

    public UserFactory() {
    }


    public User getNewUser(String type){
        User user = null;
        if (type.equals("employee")){
            user = new Employee();
        }
        else if(type.equals("patient")){
            user = new Patient();
        }
        return user;
    }
}
