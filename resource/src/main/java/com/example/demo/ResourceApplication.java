package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@SpringBootApplication
@RestController
@EnableResourceServer
public class ResourceApplication extends WebSecurityConfigurerAdapter {

  @RequestMapping("/")
  public Message home() {
    return new Message("Hello World");
  }

  public static void main(String[] args) {
    SpringApplication.run(ResourceApplication.class, args);
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
