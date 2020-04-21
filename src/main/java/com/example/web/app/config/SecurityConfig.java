package com.example.web.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http .csrf().disable();

        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/csrf").permitAll()
                .antMatchers("/v2/api-docs").hasRole("ADMIN")
                .antMatchers("/swagger-ui.html").hasRole("ADMIN")
                .antMatchers("/swagger-ui.html/**").hasRole("ADMIN")
                .antMatchers("/swagger-resources/**").hasRole("ADMIN")
                .antMatchers("/webjars/springfox-swagger-ui/**").hasRole("ADMIN")
                .antMatchers("/user_add.html").permitAll()
                .antMatchers("/site.html").hasAnyRole("ADMIN","USER")
                .antMatchers("/css/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/requestmethod").permitAll()
                .antMatchers("/index.html").permitAll()
                .antMatchers("/SelectUser").hasAnyRole("ADMIN","USER")
                .antMatchers("/CheckidF").hasAnyRole("ADMIN","USER")
                .antMatchers("/CheckidB").hasAnyRole("ADMIN","USER")
                .antMatchers("/GetMe").hasAnyRole("ADMIN","USER")
                .antMatchers("/aboutme.html").hasAnyRole("ADMIN","USER")
                .antMatchers("/admin.html").hasRole("ADMIN")
                .antMatchers("/changeAdm").hasRole("ADMIN")
                .antMatchers("/ListId").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/site.html")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();




    }

}