package com.example.web.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http .csrf().disable();

        http
                .authorizeRequests()
                .antMatchers("/csrf").permitAll()
                .antMatchers("/v2/api-docs").hasRole("ADMIN")
                .antMatchers("/swagger-ui.html").hasRole("ADMIN")
                .antMatchers("/swagger-ui.html/**").hasRole("ADMIN")
                .antMatchers("/swagger-resources/**").hasRole("ADMIN")
                .antMatchers("/webjars/springfox-swagger-ui/**").hasRole("ADMIN")
                .antMatchers("/user_add**").permitAll()
                .antMatchers("/site**").hasRole("USER")
                .antMatchers("/css/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/site.html");

    }
    @Bean
    @Override
    public UserDetailsService userDetailsService(){
       UserDetails user = User.withDefaultPasswordEncoder()
               .username("user")
               .password("9810")
               .roles("USER")
               .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("9810")
                .roles("ADMIN")
                .build();



        return new InMemoryUserDetailsManager(user,admin);
    }

}