package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class AdminApplication {

  @GetMapping(value = "/{path:[^\\.]*}")
  public String redirect() {
    return "forward:/";
  }

  @RequestMapping("/user")
  public Map<String, Object> user(Principal user) {
    Map<String, Object> map = new LinkedHashMap<>();
    map.put("name", user.getName());
    map.put("roles", AuthorityUtils.authorityListToSet(((Authentication) user).getAuthorities()));
    return map;
  }

  public static void main(String[] args) {
    SpringApplication.run(AdminApplication.class, args);
  }
}
