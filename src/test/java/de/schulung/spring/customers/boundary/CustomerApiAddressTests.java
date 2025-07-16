package de.schulung.spring.customers.boundary;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.schulung.spring.customers.CustomerApiProviderApplicationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@CustomerApiProviderApplicationTest
class CustomerApiAddressTests {

  @Autowired
  MockMvc mvc;

  String existingCustomerId;

  @BeforeEach
  void setup() throws Exception {
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
    existingCustomerId = new ObjectMapper()
      .readTree(newCustomerBody)
      .path("uuid")
      .asText();
  }

  @Test
  void shouldHaveNoAddressAfterInitialization() throws Exception {

    mvc.perform(
        get("/customers/{uuid}/address", existingCustomerId)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isNotFound());

  }

  @Test
  void shouldNotSetAddressForMissingCustomer() throws Exception {

    mvc.perform(
        delete("/customers/{uuid}", existingCustomerId)
      )
      .andExpect(status().is2xxSuccessful());

    mvc.perform(
        put("/customers/{uuid}/address", existingCustomerId)
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
              {
                "street": "Musterstraße",
                "number": "1",
                "zip": "12345",
                "city": "Musterstadt"
              }
            """)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isNotFound());
  }


  @Test
  void shouldSetAddressCorrectlyAndDeleteAddressOnCustomerDeletion() throws Exception {

    mvc.perform(
        put("/customers/{uuid}/address", existingCustomerId)
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
              {
                "street": "Musterstraße",
                "number": "1",
                "zip": "12345",
                "city": "Musterstadt"
              }
            """)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isCreated());

    mvc.perform(
        put("/customers/{uuid}/address", existingCustomerId)
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
              {
                "street": "Musterstraße",
                "number": "1",
                "zip": "12345",
                "city": "Musterstadt"
              }
            """)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isNoContent());

    mvc.perform(
        get("/customers/{uuid}/address", existingCustomerId)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.street").value("Musterstraße"))
      .andExpect(jsonPath("$.number").value("1"))
      .andExpect(jsonPath("$.zip").value("12345"))
      .andExpect(jsonPath("$.city").value("Musterstadt"));

    mvc.perform(
        get("/customers")
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk());

    mvc.perform(
        delete("/customers/{uuid}", existingCustomerId)
      )
      .andExpect(status().is2xxSuccessful());

    mvc.perform(
        get("/customers/{uuid}/address", existingCustomerId)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isNotFound());

  }

  @ParameterizedTest
  @ValueSource(strings = {
    // missing comma
    """
      {
         "street": "Musterstraße",
         "number": "1"
         "zip": "12345",
         "city": "Musterstadt"
      }
      """,
    // missing street
    """
      {
         "number": "1",
         "zip": "12345",
         "city": "Musterstadt"
      }
      """,
    // street too short
    """
      {
         "street": "M",
         "number": "1",
         "zip": "12345",
         "city": "Musterstadt"
      }
      """,
    // street too long
    """
      {
         "street": "MusterstraßeMusterstraßeMusterstraßeMusterstraßeMusterstraßeMusterstraßeMusterstraßeMusterstraßeMusterstraße",
         "number": "1",
         "zip": "12345",
         "city": "Musterstadt"
      }
      """,
    // missing zip
    """
      {
         "street": "Musterstraße",
         "number": "1",
         "city": "Musterstadt"
      }
      """,
    // invalid zip
    """
      {
         "street": "Musterstraße",
         "number": "1",
         "zip": "A2345",
         "city": "Musterstadt"
      }
      """,
    // missing city
    """
      {
         "street": "Musterstraße",
         "number": "1",
         "zip": "12345",
      }
      """,
    // city too short
    """
      {
         "street": "Musterstraße",
         "number": "1",
         "zip": "12345",
         "city": ""
      }
      """,
    // city too long
    """
      {
         "street": "Musterstraße",
         "number": "1",
         "zip": "12345",
         "city": "MusterstadtMusterstadtMusterstadtMusterstadtMusterstadtMusterstadtMusterstadtMusterstadtMusterstadtMusterstadt"
      }
      """,
    // unknown attribute
    """
      {
         "street": "Musterstraße",
         "number": "1",
         "zip": "12345",
         "city": "Musterstadt",
         "gelbekatze": "gruenerfuchs"
      }
      """,
  })
  void shouldNotCreateInvalidAddress(String body) throws Exception {
    mvc.perform(
        put("/customers/{uuid}/address", existingCustomerId)
          .contentType(MediaType.APPLICATION_JSON)
          .content(body)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isBadRequest());
  }

}
