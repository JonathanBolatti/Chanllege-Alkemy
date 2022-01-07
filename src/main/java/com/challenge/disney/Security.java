/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.challenge.disney;

import com.challenge.disney.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Jonathan
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Security extends WebSecurityConfigurerAdapter {
    
    @Autowired
    public UserService userService;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin().and().authorizeRequests().antMatchers("/css/*","/img/*","/js/*").permitAll()
                 .and().formLogin()
                       .loginPage("/login")
                       .usernameParameter("email")
                       .passwordParameter("password")
                       .defaultSuccessUrl("/")
                       .loginProcessingUrl("/logincheck")
                       .failureUrl("/login?error=error")
                       .permitAll()
                 .and().logout()
                       .logoutUrl("/logout")
                       .logoutSuccessUrl("/login?logout")
                 .and().csrf().disable();
    }
    
    
}