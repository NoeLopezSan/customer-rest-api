package dev.noelopez.restdemo1.dev.noelopez.restdemo1.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dev.noelopez.restdemo1.controller.CustomerController;
import dev.noelopez.restdemo1.model.Customer;
import dev.noelopez.restdemo1.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith({ RestDocumentationExtension.class})
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                //.alwaysDo(document("{method-name}",
                //        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
    }

    @Test
    @DisplayName("When getting a customer by id then return the customer data.")
    void givenValidId_whenGetCustomerById_thenReturnCustomerObject() throws Exception {
        final var id = 1L;

        Mockito.when(customerService.findById(id))
                .thenReturn(Optional.of(Customer.Builder
                        .newCustomer()
                        .id(id)
                        .name("John Wick")
                        .status(Customer.Status.ACTIVATED)
                        .email("john.wick@gmail.com")
                        .dob(LocalDate.of(1981,9,05))
                        .withDetails("Info", true)
                        .build()));

        mockMvc.perform(get("/api/v1/customers/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("customer-get-by-id"));
    }
}
