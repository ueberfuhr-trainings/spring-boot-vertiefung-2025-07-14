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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    assertThat(output)
      .containsPattern(String.format("(?i).*Customer created.*%s.*", uuid));
  }

}
