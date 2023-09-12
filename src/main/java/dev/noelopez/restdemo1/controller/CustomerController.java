package dev.noelopez.restdemo1.controller;

import dev.noelopez.restdemo1.dto.CustomerResponse;
import dev.noelopez.restdemo1.exception.EntityNotFoundException;
import dev.noelopez.restdemo1.service.CustomerService;
import dev.noelopez.restdemo1.util.CustomerUtils;
import dev.noelopez.restdemo1.dto.CustomerRequest;
import dev.noelopez.restdemo1.model.Customer;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
    private Logger logger = LoggerFactory.getLogger(CustomerController.class);
    @Value("${application.rest.v1.url}")
    private String urlEndpointV1;
    private CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @GetMapping
    public List<CustomerResponse> findCustomers(
            @RequestParam(name="name", required=false) String name,
            @RequestParam(name="status", required=false)  Customer.Status status,
            @RequestParam(name="info", required=false) String info,
            @RequestParam(name="vip", required=false) Boolean vip) {
        return customerService
                .findByFields(Customer.Builder
                        .newCustomer()
                        .name(name)
                        .status(status)
                        .withDetails(info, vip)
                        .build())
                .stream()
                .map(CustomerUtils::convertToCustomerResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("{customerId}")
    public ResponseEntity<CustomerResponse> findCustomers(@PathVariable("customerId") Long id) {
        return customerService
                .findById(id)
                .map(CustomerUtils::convertToCustomerResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException(id, Customer.class));
    }

    @PostMapping
    public ResponseEntity<Long> addCustomer(@Valid @RequestBody CustomerRequest customerRequest)  {
        Customer customer = CustomerUtils.convertToCustomer(customerRequest);
        customer.setStatus(Customer.Status.ACTIVATED);
        customerService.save(customer);

        return ResponseEntity.created(URI.create(urlEndpointV1+"customers/"+customer.getId() )).build();
    }

    @PutMapping("{customerId}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable("customerId") Long id, @Valid @RequestBody CustomerRequest customerRequest)  {
        Customer customer = customerService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Customer.class));

        CustomerUtils.updateCustomer(customer, customerRequest);
        customerService.save(customer);

        return ResponseEntity.ok(CustomerUtils.convertToCustomerResponse(customer));
    }

    @DeleteMapping("{customerId}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable("customerId") Long id)  {
        Customer customer = customerService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Customer.class));

        customerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}