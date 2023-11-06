package dev.noelopez.restdemo1.mapper;

import dev.noelopez.restdemo1.config.filewatcher.csv.CSVRecord;
import dev.noelopez.restdemo1.dto.CustomerDetailsInfo;
import dev.noelopez.restdemo1.dto.CustomerPersonInfo;
import dev.noelopez.restdemo1.dto.CustomerRequest;
import dev.noelopez.restdemo1.dto.CustomerResponse;
import dev.noelopez.restdemo1.model.Customer;
import dev.noelopez.restdemo1.model.CustomerDetails;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static dev.noelopez.restdemo1.model.Customer.Builder.newCustomer;

public class CustomerMapper {

    public static final String DATE_FORMAT = "dd/MM/yyyy";

    public static Customer mapToCustomer(CustomerRequest customerRequest) {
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
    public static CustomerResponse mapToCustomerResponse(Customer customer) {
        return  new CustomerResponse(
                customer.getId(),
                customer.getStatus(),
                new CustomerPersonInfo(
                        customer.getName(),
                        customer.getEmail(),
                        customer.getDateOfBirth().format(DateTimeFormatter.ofPattern(DATE_FORMAT))),
                new CustomerDetailsInfo(customer.getDetails().getInfo(),customer.getDetails().isVip())
        );
    }

    public static Customer mapToCustomer(CSVRecord record) {
        var customerRequest = new CustomerRequest(
                record.get("name"),
                record.get("email"),
                LocalDate.parse(record.get("dob"), DateTimeFormatter.ofPattern(CustomerMapper.DATE_FORMAT)),
                record.get("info"),
                Boolean.valueOf(record.get("vip"))
        );

        return mapToCustomer(customerRequest);
    }
}
