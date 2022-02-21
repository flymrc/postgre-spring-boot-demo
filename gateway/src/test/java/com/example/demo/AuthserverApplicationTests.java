package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthserverApplicationTests {

  @LocalServerPort
  private int port;

  private TestRestTemplate template = new TestRestTemplate();

  @Test
  public void homePageProtected() {
    ResponseEntity<String> response = template.getForEntity("http://localhost:" + port + "/uaa/", String.class);
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    String auth = response.getHeaders().getFirst("WWW-Authenticate");
    assertTrue("Wrong header: " + auth, auth.startsWith("Bearer realm=\""));
  }

  @Test
  public void userEndpointProtected() {
    ResponseEntity<String> response = template.getForEntity("http://localhost:" + port + "/uaa/user", String.class);
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    String auth = response.getHeaders().getFirst("WWW-Authenticate");
    assertTrue("Wrong header: " + auth, auth.startsWith("Bearer realm=\""));
  }

  @Test
  public void authorizationRedirects() {
    ResponseEntity<String> response = template.getForEntity("http://localhost:" + port + "/uaa/oauth/authorize", String.class);
    assertEquals(HttpStatus.FOUND, response.getStatusCode());
    String location = response.getHeaders().getFirst("Location");
    assertTrue("Wrong header: " + location, location.startsWith("http://localhost:" + port + "/uaa/login"));
  }

  @Test
  public void loginSucceeds() {
    ResponseEntity<String> response = template.getForEntity("http://localhost:" + port + "/uaa/login", String.class);
    String csrf = getCsrf(response.getBody());
    MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
    form.set("username", "user");
    form.set("password", "password");
    form.set("_csrf", csrf);
    HttpHeaders headers = new HttpHeaders();
    headers.put("COOKIE", response.getHeaders().get("Set-Cookie"));
    RequestEntity<MultiValueMap<String, String>> request =
      new RequestEntity<>(form, headers, HttpMethod.POST, URI.create("http://localhost:" + port + "/uaa/login"));
    ResponseEntity<Void> location = template.exchange(request, Void.class);
    assertEquals("http://localhost:" + port + "/uaa/", location.getHeaders().getFirst("Location"));
  }

  private String getCsrf(String soup) {
    Matcher matcher = Pattern.compile("(?s).*name=\"_csrf\".*?value=\"([^\"]+).*").matcher(soup);
    if (matcher.matches()) {
      return matcher.group(1);
    }
    return null;
  }
}