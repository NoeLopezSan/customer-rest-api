package dev.noelopez.restdemo1.util;

import dev.noelopez.restdemo1.dto.CustomerRequest;
import dev.noelopez.restdemo1.dto.CustomerResponse;
import dev.noelopez.restdemo1.model.Customer;

public class CustomerUtils {
    public static Customer convertToCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setName(customerRequest.name());
        customer.setEmail(customerRequest.email());
        customer.setDateOfBirth(customerRequest.dateOfBirth());
        return customer;
    }

    public static CustomerResponse convertToCustomerResponse(Customer customer) {
        return  new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getDateOfBirth()
        );
    }
}
