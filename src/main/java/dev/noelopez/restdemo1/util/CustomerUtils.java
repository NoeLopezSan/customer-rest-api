package dev.noelopez.restdemo1.util;

import dev.noelopez.restdemo1.dto.CustomerDetailsInfo;
import dev.noelopez.restdemo1.dto.CustomerPersonInfo;
import dev.noelopez.restdemo1.dto.CustomerRequest;
import dev.noelopez.restdemo1.dto.CustomerResponse;
import dev.noelopez.restdemo1.model.Customer;
import dev.noelopez.restdemo1.model.CustomerDetails;

import java.time.format.DateTimeFormatter;

import static dev.noelopez.restdemo1.model.Customer.Builder.newCustomer;

public class CustomerUtils {
    public static Customer convertToCustomer(CustomerRequest customerRequest) {
        return newCustomer()
                .name(customerRequest.name())
                .email(customerRequest.email())
                .dob(customerRequest.dateOfBirth())
                .withDetails(customerRequest.info(),customerRequest.vip())
                .build();
    }

    public static Customer updateCustomer(Customer customer, CustomerRequest customerRequest) {
        customer.setName(customerRequest.name());
        customer.setEmail(customerRequest.email());
        customer.setDateOfBirth(customerRequest.dateOfBirth());

        CustomerDetails details = customer.getDetails();
        details.setInfo(customerRequest.info());
        details.setVip(customerRequest.vip());
        customer.setDetails(details);
        return customer;
    }
    public static CustomerResponse convertToCustomerResponse(Customer customer) {
        return  new CustomerResponse(
                customer.getId(),
                customer.getStatus(),
                new CustomerPersonInfo(
                        customer.getName(),
                        customer.getEmail(),
                        customer.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))),
                new CustomerDetailsInfo(customer.getDetails().getInfo(),customer.getDetails().isVip())
        );
    }
}
