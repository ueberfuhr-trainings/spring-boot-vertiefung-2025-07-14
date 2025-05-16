package de.schulung.spring.customers.boundary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
  properties = {
    "application.cors.enabled=true",
    "application.cors.allowed-origins=*.swagger.io"
  }
)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class CorsTests {

  @Autowired
  MockMvc mockMvc;

  @Test
  void shouldAllowSwaggerIoScripts() throws Exception {
    mockMvc
      .perform(
        options("/customers")
          .header(HttpHeaders.ORIGIN, "editor.swagger.io")
          .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "POST")
          .header(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS, HttpHeaders.CONTENT_TYPE)
      )
      .andExpect(status().isOk());
  }

  @Test
  void shouldNotAllowOtherScripts() throws Exception {
    mockMvc
      .perform(
        options("/customers")
          .header(HttpHeaders.ORIGIN, "google.com")
          .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "POST")
          .header(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS, HttpHeaders.CONTENT_TYPE)
      )
      .andExpect(status().isForbidden());
  }

}
