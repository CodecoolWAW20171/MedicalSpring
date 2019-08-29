package com.medbis.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


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
    private Set<Visit> visitsEmployee;

    public String getPermissions() {
        return permissions;
    }

    private String permissions;

    public Employee(String password, @NotEmpty String login, String permissions) {
        this.id = id;
        this.password = password;
        this.login = login;
        this.passwordChanged = false;
        this.permissions = permissions;

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

//    public Set<Visit> getVisitsEmployee() {
//        return visitsEmployee;
//    }
//
//    public void setVisitsEmployee(Set<Visit> visitsEmployee) {
//        this.visitsEmployee = visitsEmployee;
//    }


    public boolean isPasswordChanged() {
        return passwordChanged;
    }

    public void setPasswordChanged(boolean passwordChanged) {
        this.passwordChanged = passwordChanged;
    }
}
