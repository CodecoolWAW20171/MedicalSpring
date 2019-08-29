package com.medbis.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserPrinicipalDetailsService userPrinicipalDetailsService;
    private LoginSuccessHandler loginSuccessHandler;
    private CustomAccessDeniedHandler  customAccessDeniedHandler;

    SecurityConfiguration(UserPrinicipalDetailsService userPrinicipalDetailsService, LoginSuccessHandler loginSuccessHandler, CustomAccessDeniedHandler customAccessDeniedHandler){
        this.userPrinicipalDetailsService = userPrinicipalDetailsService;
        this.loginSuccessHandler = loginSuccessHandler;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth
                .authenticationProvider(authenticationProvider());
    }


@Override
public void configure(HttpSecurity http) throws Exception{
    http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/css/**", "/js/**","/signin", "/login").permitAll()
            .antMatchers("/employees/change-password", "/employees/change-password-form", "/employees/show-employee-details").hasAnyRole("NURSE", "ADMIN")
            .antMatchers( "/categories/**", "/treatments/**", "/diseases/**", "/employees/**", "/medicines/**", "/visits/all-visits-list").hasRole("ADMIN")
            .and()
            .formLogin().loginProcessingUrl("/signin").loginPage("/login")
            .usernameParameter("username").passwordParameter("password").successHandler(loginSuccessHandler)
            .and()
            .logout().logoutRequestMatcher(antPathRequestMatcher()).logoutSuccessUrl("/login")
            .and()
            .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
            .and()
            .authorizeRequests()
            .anyRequest()
            .authenticated();
    }



    private AntPathRequestMatcher antPathRequestMatcher(){
        return new AntPathRequestMatcher("/logout", "GET");
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userPrinicipalDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
