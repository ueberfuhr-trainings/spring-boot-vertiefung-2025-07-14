package de.schulung.spring.customers.boundary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StaticResourcesTests {

  @Autowired
  MockMvc mvc;

  @Test
  void shouldHaveLandingPage() throws Exception {
    mvc
      .perform(get("/"))
      .andExpect(status().isOk());
  }

  @Test
  void shouldHaveOpenApi() throws Exception {
    mvc
      .perform(get("/openapi.yml"))
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_YAML));
  }

}
