package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
      .withUser("user").password("password").roles("USER").and()
      .withUser("admin").password("admin").roles("USER", "ADMIN", "READER", "WRITER").and()
      .withUser("audit").password("audit").roles("USER", "ADMIN", "READER");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .httpBasic().and()
      .logout().and()
      .authorizeRequests()
      .antMatchers("/index.html", "/").permitAll()
      .antMatchers("/*.js", "/*.css", "/favicon.ico").permitAll()
      .antMatchers("/admin/**").hasRole("ADMIN")
      .anyRequest().authenticated().and()
      .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
  }
}
