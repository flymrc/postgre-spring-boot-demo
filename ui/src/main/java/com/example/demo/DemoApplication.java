package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@SpringBootApplication
@RestController
public class DemoApplication {
  private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

  @RequestMapping("/token")
  public Map<String,String> token(HttpSession session) {
    return Collections.singletonMap("token", session.getId());
  }

  @RequestMapping("/user")
  public Principal user(Principal user) {
    return user;
  }

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }
}
