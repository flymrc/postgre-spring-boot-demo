package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@SpringBootApplication
@RestController
@EnableRedisHttpSession
public class ResourceApplication extends WebSecurityConfigurerAdapter {

  @RequestMapping("/")
  @CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = {"x-auth-token", "x-requested-with", "x-xsrf-token"})
  public Message home() {
    return new Message("Hello World");
  }

  public static void main(String[] args) {
    SpringApplication.run(ResourceApplication.class, args);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic().disable();
    http.authorizeRequests()
      .anyRequest().authenticated()
      .and().sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.NEVER);
  }
}

class Message {
  private final String id = UUID.randomUUID().toString();
  private final String content;

  public Message(String content) {
    this.content = content;
  }

  public String getId() {
    return id;
  }

  public String getContent() {
    return content;
  }
}
