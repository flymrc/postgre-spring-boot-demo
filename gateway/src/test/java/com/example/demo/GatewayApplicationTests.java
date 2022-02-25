package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GatewayApplicationTests {

  @LocalServerPort
  private int port;

  private TestRestTemplate template = new TestRestTemplate();

  @Test
  public void homePageLoads() {
    ResponseEntity<String> response = template.getForEntity("http://localhost:" + port + "/", String.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }
}
