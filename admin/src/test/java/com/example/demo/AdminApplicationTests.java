package com.example.demo;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(properties="security.user.password:foo", webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminApplicationTests {

  @LocalServerPort
  private int port;

  private TestRestTemplate template = new TestRestTemplate();

	@Test
	public void homePageLoads() {
    ResponseEntity<String> response = template.getForEntity("http://localhost:{port}/", String.class, port);
    assertEquals(HttpStatus.OK, response.getStatusCode());
	}

  @Test
  public void userEndpointProtected() {
    ResponseEntity<String> response = template.getForEntity("http://localhost:" + port + "/user", String.class);
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }

  @Test
  public void resourceEndpointProtected() {
    ResponseEntity<String> response = template.getForEntity("http://localhost:" + port + "/resource", String.class);
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }

  @Test
  public void loginSucceeds() {
    TestRestTemplate template = new TestRestTemplate("user", "foo");
    ResponseEntity<String> response = template.getForEntity("http://localhost:" + port + "/user", String.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }
}
