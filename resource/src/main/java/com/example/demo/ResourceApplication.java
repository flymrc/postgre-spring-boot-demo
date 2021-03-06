package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.TraceProperties;
import org.springframework.boot.actuate.trace.TraceRepository;
import org.springframework.boot.actuate.trace.WebRequestTraceFilter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class ResourceApplication extends WebSecurityConfigurerAdapter {

  private String message = "Hello World";
  private List<Change> changes = new ArrayList<>();

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public Message home() {
    return new Message(message);
  }

  @RequestMapping(value = "/changes", method = RequestMethod.GET)
  public List<Change> changes() {
    return changes;
  }

  @RequestMapping(value = "/changes", method = RequestMethod.POST)
  public Message update(@RequestBody Message map, Principal principal) {
    if (map.getContent() != null) {
      message = map.getContent();
      changes.add(new Change(principal.getName(), message));
      while (changes.size() > 10) {
        changes.remove(0);
      }
    }
    return new Message(message);
  }

  public static void main(String[] args) {
    SpringApplication.run(ResourceApplication.class, args);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic().disable()
      .authorizeRequests()
      .antMatchers(HttpMethod.POST, "/**").hasRole("WRITER")
      .anyRequest().authenticated().and()
      .csrf()
      .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
  }

  @Bean
  public WebRequestTraceFilter webRequestLoggingFilter(
    ErrorAttributes errorAttributes,
    TraceRepository traceRepository,
    TraceProperties traceProperties
  ) {
    WebRequestTraceFilter filter = new WebRequestTraceFilter(traceRepository, traceProperties);
    if (errorAttributes != null) {
      filter.setErrorAttributes(errorAttributes);
    }
    filter.setOrder(SecurityProperties.DEFAULT_FILTER_ORDER - 1);
    return filter;
  }
}
