package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@SpringBootApplication
@RestController
public class DemoApplication {
  private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

  @RequestMapping("/user")
  public Principal user(Principal user) {
    return user;
  }

//  @GetMapping(value = "/{path:[^\\.]*}")
//  public String redirect() {
//    return "forward:/";
//  }

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

//  @Bean
//  @Order(SecurityProperties.BASIC_AUTH_ORDER)
//  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//    return http.httpBasic().and()
//      .logout().and()
//      .authorizeExchange()
//      .pathMatchers("/index.html", "/", "/home", "/login").permitAll()
//      .pathMatchers("/*.js", "/*.css", "/favicon.ico").permitAll()
//      .anyExchange().authenticated().and()
//      .csrf().csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse()).and()
//      .build();
//  }

  @Bean
  public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
    return builder.routes()
      .route("resource", r -> r
        .path("/resource")
        .filters(f -> f.rewritePath("/resource", "/"))
        .uri("http://localhost:9000")
      )
      .build();
  }
}
