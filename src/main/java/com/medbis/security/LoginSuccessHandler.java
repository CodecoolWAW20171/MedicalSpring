package com.medbis.security;

import com.medbis.entity.Employee;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
        Employee loggedUser = userDetails.getEmployee();
        if (loggedUser.isPasswordChanged()) {
            response.sendRedirect("/employees/show-employee-details?id=" + loggedUser.getId());
        } else {
            response.sendRedirect("/employees/change-password-form");
        }
    }



}
