package de.schulung.spring.customers.boundary;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerApiTests {

  @Autowired
  MockMvc mvc;

  // GET /customers -> 200
  @Test
  void shouldGetCustomers() throws Exception {
    var newCustomerBody = mvc.perform(
        post("/customers")
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
              {
                "name": "Tom Mayer",
                "birthdate": "2005-05-12",
                "state": "active"
              }
            """)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isCreated())
      .andReturn()
      .getResponse()
      .getContentAsString();
    var uuid = new ObjectMapper()
      .readTree(newCustomerBody)
      .path("uuid")
      .asText();

    mvc
      .perform(
        get("/customers")
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(
        jsonPath(
          String
            .format(
              "$[?(@.uuid == '%s' && @.name == '%s' && @.birthdate == '%s' && @.state == '%s')]",
              uuid,
              "Tom Mayer",
              "2005-05-12",
              "active"
            )
        )
          .exists()
      );

  }

  // GET /customers -> 406
  @Test
  void shouldNotGetCustomersAsXml() throws Exception {
    mvc
      .perform(
        get("/customers")
          .accept(MediaType.APPLICATION_XML)
      )
      .andExpect(status().isNotAcceptable());
  }

  @Test
  void shouldNotGetCustomersWithInvalidStateParameter() throws Exception {
    mvc
      .perform(
        get("/customers")
          .param("state", "gelbekatze")
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isBadRequest());
  }

  @RequiredArgsConstructor
  @Getter
  private enum StateParameter {
    ACTIVE("active"),
    LOCKED("locked"),
    DISABLED("disabled");
    private final String parameterValue;
  }

  @ParameterizedTest
  @EnumSource(StateParameter.class)
  void shouldGetCustomersByState(StateParameter parameter) throws Exception {
    var newCustomerBody = mvc.perform(
        post("/customers")
          .contentType(MediaType.APPLICATION_JSON)
          .content(String.format(
              """
                  {
                    "name": "Tom Mayer",
                    "birthdate": "2005-05-12",
                    "state": "%s"
                  }
                """,
              parameter.getParameterValue()
            )
          )
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isCreated())
      .andReturn()
      .getResponse()
      .getContentAsString();
    var uuid = new ObjectMapper()
      .readTree(newCustomerBody)
      .path("uuid")
      .asText();

    // we need to find it with the state parameter
    mvc
      .perform(
        get("/customers")
          .param("state", parameter.getParameterValue())
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(
        jsonPath(
          String
            .format(
              "$[?(@.uuid == '%s')]",
              uuid
            )
        )
          .exists()
      );
    // we must not find it with other state parameters
    for (StateParameter p : StateParameter.values()) {
      if (p != parameter) {
        mvc
          .perform(
            get("/customers")
              .param("state", p.getParameterValue())
              .accept(MediaType.APPLICATION_JSON)
          )
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(
            jsonPath(
              String
                .format(
                  "$[?(@.uuid == '%s')]",
                  uuid
                )
            )
              .doesNotExist()
          );
      }
    }

  }

  /*
   * POST /customers
   * Content-Type: application/json
   * Accept: application/json
   * {
        "name": "Tom Mayer",
        "birthdate": "2005-05-12",
        "state": "active"
      }
   * ->
   * 201
   * Content-Type: application/json
   * {
        "uuid": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "name": "Tom Mayer",
        "birthdate": "2005-05-12",
        "state": "active"
      }
   * Location-Header existiert
   */
  @Test
  void shouldCreateReturnAndDeleteCustomer() throws Exception {
    var location = mvc.perform(
        post("/customers")
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
              {
                "name": "Tom Mayer",
                "birthdate": "2005-05-12",
                "state": "active"
              }
            """)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isCreated())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.name").value("Tom Mayer"))
      .andExpect(jsonPath("$.birthdate").value("2005-05-12"))
      .andExpect(jsonPath("$.state").value("active"))
      .andExpect(jsonPath("$.uuid").isNotEmpty())
      .andExpect(header().exists("Location"))
      .andReturn()
      .getResponse()
      .getHeader("Location");

    assertThat(location)
      .isNotBlank();

    // read customer - 200
    mvc
      .perform(
        get(location)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.name").value("Tom Mayer"))
      .andExpect(jsonPath("$.birthdate").value("2005-05-12"))
      .andExpect(jsonPath("$.state").value("active"));

    // delete customer - 204
    mvc
      .perform(
        delete(location)
      )
      .andExpect(status().isNoContent());

    // delete customer again - 404
    mvc
      .perform(
        delete(location)
      )
      .andExpect(status().isNotFound());

    // read customer - 404
    mvc
      .perform(
        get(location)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isNotFound());

  }

  @Test
  void shouldNotCreateCustomerAsXml() throws Exception {
    mvc.perform(
        post("/customers")
          .contentType(MediaType.APPLICATION_XML)
          .content("<customer/>")
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isUnsupportedMediaType());
  }

  @Test
  void shouldNotCreateCustomerAndReturnXml() throws Exception {
    mvc.perform(
        post("/customers")
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
              {
                "name": "Tom Mayer",
                "birthdate": "2005-05-12",
                "state": "active"
              }
            """)
          .accept(MediaType.APPLICATION_XML)
      )
      .andExpect(status().isNotAcceptable());
  }

  @ParameterizedTest
  @ValueSource(strings = {
    // missing comma
    """
      {
         "name": "Tom Mayer"
         "birthdate": "2005-05-12",
         "state": "active"
      }
      """,
    // invalid date
    """
      {
         "name": "Tom Mayer",
         "birthdate": "gelbekatze",
         "state": "active"
      }
      """,
    // invalid state
    """
      {
         "name": "Tom Mayer",
         "birthdate": "2005-05-12",
         "state": "gelbekatze"
      }
      """,
    // missing birthdate date
    """
      {
         "name": "Tom Mayer",
         "state": "active"
      }
      """,
    // missing name
    """
      {
         "birthdate": "2005-05-12",
         "state": "active"
      }
      """,
    // with uuid
    """
      {
         "uuid": "bf7f440b-c9de-4eb8-91f4-43108277e9a3",
         "name": "Tom Mayer",
         "birthdate": "2005-05-12",
         "state": "active"
      }
      """,
  })
  void shouldNotCreateInvalidCustomer(String body) throws Exception {
    mvc.perform(
        post("/customers")
          .contentType(MediaType.APPLICATION_JSON)
          .content(body)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isBadRequest());
  }

  @Test
  void shouldCreateCustomerWithDefaultState() throws Exception {
    mvc.perform(
        post("/customers")
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
              {
                "name": "Tom Mayer",
                "birthdate": "2005-05-12"
              }
            """)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isCreated())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.state").value("active"));
  }

}
