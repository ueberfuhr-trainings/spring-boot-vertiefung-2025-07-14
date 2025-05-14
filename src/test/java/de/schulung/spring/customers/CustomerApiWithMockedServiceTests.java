package de.schulung.spring.customers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerApiWithMockedServiceTests {

  @Autowired
  MockMvc mvc;
  @MockitoBean
  CustomersService customersService;

  @Test
  void shouldReturnNotFoundWhenCustomerNotExists() throws Exception {

    var uuid = UUID.randomUUID();
    when(customersService.findById(uuid))
      .thenReturn(Optional.empty());

    mvc
      .perform(
        get("/customers/{uuid}", uuid)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isNotFound());

  }

  @Test
  void shouldNotCreateCustomerOnValidationError() throws Exception {
    mvc.perform(
        post("/customers")
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
             {
                "birthdate": "2005-05-12",
               "state": "active"
            }
            """)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isBadRequest());

    verify(customersService, never()).create(any());

  }


}
