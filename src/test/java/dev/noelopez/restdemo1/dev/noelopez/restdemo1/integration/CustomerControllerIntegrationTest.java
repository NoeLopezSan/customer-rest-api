package dev.noelopez.restdemo1.dev.noelopez.restdemo1.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({ RestDocumentationExtension.class})
@SpringBootTest
public class CustomerControllerIntegrationTest {
    private MockMvc mockMvc;
    private FieldDescriptor[] customerResponseFields = {
            fieldWithPath("id").description("Customer id"),
            fieldWithPath("status").description("Customer's status"),
            subsectionWithPath("personInfo").description("The customer's personal info section"),
            fieldWithPath("personInfo.name").description("Customer's name"),
            fieldWithPath("personInfo.email").description("Customer's email"),
            fieldWithPath("personInfo.dateOfBirth").description("Customer's Date Of Birth"),
            subsectionWithPath("detailsInfo").description("The customer's details info section"),
            fieldWithPath("detailsInfo.info").description("Customer's detailed info"),
            fieldWithPath("detailsInfo.vip").description("Customer's vip flag")};
    private ParameterDescriptor[] customerSearchQueryParams = {
            parameterWithName("name").description("Customer name filter"),
            parameterWithName("status").description("Customer status filter. Values  ACTIVATED/DEACTIVATED/SUSPENDED"),
            parameterWithName("info").description("Customer info filter"),
            parameterWithName("vip").description("Customer vip filter. Values true/false")};
    private ParameterDescriptor customerIdParam = parameterWithName("id").description("Customer id");

    private FieldDescriptor[] customerRequestFields = {
            fieldWithPath("name").description("Customer's name"),
            fieldWithPath("email").description("Customer's email"),
            fieldWithPath("dateOfBirth").description("Customer's Date Of Birth"),
            fieldWithPath("info").description("Customer's detailed info"),
            fieldWithPath("vip").description("Customer's vip flag")};

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .build();
    }

    @Test
    @DisplayName("When getting a customer by id then return the customer data.")
    void givenValidId_whenGetCustomerById_thenReturnCustomer() throws Exception {
        final var id = 1L;

        mockMvc.perform(get("/api/v1/customers/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("customer-get-by-id",
                        pathParameters(customerIdParam),
                        responseFields(customerResponseFields)));
    }

    @Test
    @DisplayName("When getting customers then return list of customers.")
    void givenParams_whenGetCustomers_thenReturnListOfCustomers() throws Exception {
        final var queryParams = "?name=name&status=ACTIVATED&info=info&vip=true";

        mockMvc.perform(get("/api/v1/customers"+queryParams)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("customer-search-customers",
                        queryParameters(customerSearchQueryParams),
                        responseFields(fieldWithPath("[]").description("An array of customers"))
                                .andWithPrefix("[].", customerResponseFields)));
    }

    @Test
    @DisplayName("When adding a customer then return created with location.")
    void givenCustomerRequest_whenAddCustomer_thenReturnCreated() throws Exception {
        final var body = """
                {
                      "name" : "Lawrence Lesnar",
                      "email" : "lawrence.lesnar@gmail.com123",
                      "dateOfBirth" : "2001-03-28",
                      "info" : "more info here",
                      "vip" : "true"
                }
                """;

        mockMvc.perform(post("/api/v1/customers")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("customer-add",
                        requestFields(customerRequestFields),
                        responseHeaders(headerWithName("location")
                                        .description("The location/URL of the new customer."))));
    }

    @Test
    @DisplayName("When updating a customer then return ok.")
    void givenIdAndCustomerRequest_whenUpdateCustomer_thenReturnOk() throws Exception {
        final var id = 2L;
        final var body = """
                {
                      "name" : "Lisa McGregor",
                      "email" : "lisa.mcgregor@gmail.com123",
                      "dateOfBirth" : "1992-11-05",
                      "info" : "more info here",
                      "vip" : "false"
                }
                """;

        mockMvc.perform(put("/api/v1/customers/{id}",id)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("customer-update",
                        pathParameters(customerIdParam),
                        requestFields(customerRequestFields),
                        responseFields(customerResponseFields)));
    }

    @Test
    @DisplayName("When deleting a customer by id then return no content.")
    void givenValidId_whenDeleteCustomerById_thenReturnNoContent() throws Exception {
        final var id = 3L;

        mockMvc.perform(delete("/api/v1/customers/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(document("customer-delete",
                        pathParameters(customerIdParam)));
    }
}
