package de.schulung.spring.customers.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.schulung.spring.customers.CustomerApiProviderApplicationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@CustomerApiProviderApplicationTest
@ExtendWith(OutputCaptureExtension.class)
class CustomerEventsLoggingTests {

  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;

  @Test
  void shouldLogEventOnCreateValidCustomer(CapturedOutput output) throws Exception {
    var responseJson = mockMvc
      .perform(
        post("/customers")
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
                        {
                          "name": "Tom Mayer",
                          "birthdate": "1985-07-03",
                          "state": "active"
                        }
            """)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isCreated())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andReturn()
      .getResponse()
      .getContentAsString();
    var uuid = objectMapper
      .readTree(responseJson)
      .get("uuid")
      .asText();
    assertThat(uuid)
      .isNotBlank();

    await()
      .atMost(Duration.ofSeconds(5))
      .untilAsserted(
        () -> assertThat(output)
          .containsPattern(String.format("(?i).*Customer created.*%s.*", uuid))
      );

  }

  @Test
  void shouldNotLogEventOnCreateInvalidCustomer(CapturedOutput output) throws Exception {
    mockMvc
      .perform(
        post("/customers")
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
                        {
                          "birthdate": "1985-07-03",
                          "state": "active"
                        }
            """)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isBadRequest());

    await()
      .during(Duration.ofSeconds(2))
      .atMost(Duration.ofSeconds(5))
      .untilAsserted(
        () -> assertThat(output)
          .doesNotContainPattern("(?i).*Customer created.*")
      );
  }


  @Test
  void shouldLogEventOnUpdateValidCustomer(CapturedOutput output) throws Exception {
    var responseJson = mockMvc
      .perform(
        post("/customers")
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
                        {
                          "name": "Tom Mayer",
                          "birthdate": "1985-07-03",
                          "state": "active"
                        }
            """)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isCreated())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andReturn()
      .getResponse()
      .getContentAsString();
    var uuid = objectMapper
      .readTree(responseJson)
      .get("uuid")
      .asText();

    mockMvc
      .perform(
        put("/customers/{uuid}", uuid)
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
                        {
                          "name": "Tom Mayer",
                          "birthdate": "1986-07-03",
                          "state": "active"
                        }
            """)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().is2xxSuccessful());

    await()
      .atMost(Duration.ofSeconds(5))
      .untilAsserted(
        () -> assertThat(output)
          .containsPattern(String.format("(?i).*Customer updated.*%s.*", uuid))
      );
  }

  @Test
  void shouldLogEventOnDeleteCustomer(CapturedOutput output) throws Exception {
    var responseJson = mockMvc
      .perform(
        post("/customers")
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
                        {
                          "name": "Tom Mayer",
                          "birthdate": "1985-07-03",
                          "state": "active"
                        }
            """)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isCreated())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andReturn()
      .getResponse()
      .getContentAsString();
    var uuid = objectMapper
      .readTree(responseJson)
      .get("uuid")
      .asText();

    mockMvc
      .perform(
        delete("/customers/{uuid}", uuid)
      )
      .andExpect(status().is2xxSuccessful());

    await()
      .atMost(Duration.ofSeconds(5))
      .untilAsserted(
        () -> assertThat(output)
          .containsPattern(String.format("(?i).*Customer deleted.*%s.*", uuid))
      );
  }

}
