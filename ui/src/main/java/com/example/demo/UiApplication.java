package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@SpringBootApplication
@Controller
public class UiApplication {

  @GetMapping(value = "/{path:[^\\.]*}")
  public String redirect() {
    return "forward:/";
  }

  @RequestMapping("/user")
  @ResponseBody
  public Map<String, String> user(Principal user) {
    return Collections.singletonMap("name", user.getName());
  }

  public static void main(String[] args) {
    SpringApplication.run(UiApplication.class, args);
  }
}
