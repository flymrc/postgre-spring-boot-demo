package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResourceApplicationTests {

  @LocalServerPort
  private int port;

  private TestRestTemplate template = new TestRestTemplate();

	@Test
	public void resourceLoads() {
    ResponseEntity<String> response = template.getForEntity("http://localhost:{port}/resource/", String.class, port);
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    String auth = response.getHeaders().getFirst("WWW-Authenticate");
    assertTrue("Wrong header: " + auth , auth.startsWith("Bearer"));
	}
}
