package com.medbis.security;

import com.medbis.entity.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class UserPrincipal implements UserDetails {

    public UserPrincipal(Employee employee) {
        this.employee = employee;
    }

    private Employee employee;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        employee.getPermissionsList().forEach(p -> {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" +p);
            authorities.add(authority);
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        try {
            return this.employee.getPassword();
        } catch (NullPointerException err) {
            return "redirect:/login";
        }
    }

    @Override
    public String getUsername() {
        return this.employee.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Employee getEmployee() {
        return this.employee;
    }
}
