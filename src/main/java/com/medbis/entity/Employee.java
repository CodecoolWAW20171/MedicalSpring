package com.medbis.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;


@Component
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "employees", schema = "public")
public class Employee extends User{

    /*
        plec oznaczac jako M/F zeby ujednolicic
*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private int id;

    @Column(name="password")
    private String password;

    @Column(name="login")
    @NotEmpty
    private String login;

    @Column(name="status")
    private boolean passwordChanged;

    @OneToMany(mappedBy="employee", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JsonManagedReference
    private Set<Visit> visitsEmployee;

    public String getPermissions() {
        return permissions;
    }

    private String permissions;



    public void setTreatmentResultMap(Map<Treatment, Integer> treatmentResultMap) {
        this.treatmentResultMap = treatmentResultMap;
    }

    @Override
    public Map<Treatment, Integer> getTreatmentResultMap() {
        try{
            return this.treatmentResultMap;
        }
        catch (NullPointerException err){
            return new HashMap<>();
        }
    }




    @Override
    public Integer getResultOfTheTreatment(Treatment treatment){
        try {
            return this.getTreatmentResultMap().get(treatment);
        }
        catch (NullPointerException err){
            return 0;
        }

    }


    @Transient
    private Map<Treatment, Integer> treatmentResultMap;


    public Employee(String password, @NotEmpty String login, String permissions) {
        this.password = password;
        this.login = login;
        this.passwordChanged = false;
        this.permissions = permissions;
        this.treatmentResultMap = new HashMap<>();
    }

    public List<String> getPermissionsList() throws NullPointerException {
        try{
           return Arrays.asList(permissions.split(","));
        }
        catch (NullPointerException err){
            return new ArrayList<>();
        }
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() throws NullPointerException {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin(){
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public boolean isPasswordChanged() {
        return passwordChanged;
    }

    public void setPasswordChanged(boolean passwordChanged) {
        this.passwordChanged = passwordChanged;
    }

    public void raiseResultOfTreatmentDone(Treatment treatment){
        int result = 0;
        try {
            result = this.treatmentResultMap.get(treatment) + 1;
        }
        catch (NullPointerException err){
            result = 1;
        }
        finally {
            this.treatmentResultMap.put(treatment, result);
        }

    }

    public void generateMapOfTreatments(List<Treatment> treatments) {
        Map<Treatment, Integer> treatmentIntegerMap = new HashMap<>();
        for (Treatment treatment : treatments) {
            treatmentIntegerMap.put(treatment, 0);
        }
        setTreatmentResultMap(treatmentIntegerMap);
    }

}
