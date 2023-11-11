package dev.noelopez.restdemo1.controller;

import dev.noelopez.restdemo1.dto.CustomerRequest;
import dev.noelopez.restdemo1.dto.CustomerResponse;
import dev.noelopez.restdemo1.exception.EntityNotFoundException;
import dev.noelopez.restdemo1.mapper.CustomerMapper;
import dev.noelopez.restdemo1.model.Customer;
import dev.noelopez.restdemo1.service.CustomerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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
    @GetMapping(produces = APPLICATION_JSON_VALUE)
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
                .map(CustomerMapper::mapToCustomerResponse)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "{customerId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponse> findCustomer(@PathVariable("customerId") Long id) {
        return customerService
                .findById(id)
                .map(CustomerMapper::mapToCustomerResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException(id, Customer.class));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> addCustomer(@Valid @RequestBody CustomerRequest customerRequest)  {
        Customer customer = CustomerMapper.mapToCustomer(customerRequest);
        customer.setStatus(Customer.Status.ACTIVATED);
        customerService.save(customer);

        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{/id}")
                .buildAndExpand(customer.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("{customerId}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable("customerId") Long id, @Valid @RequestBody CustomerRequest customerRequest)  {
        Customer customer = customerService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Customer.class));

        CustomerMapper.updateCustomer(customer, customerRequest);
        customerService.save(customer);

        return ResponseEntity.ok(CustomerMapper.mapToCustomerResponse(customer));
    }

    @DeleteMapping("{customerId}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable("customerId") Long id)  {
        Customer customer = customerService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Customer.class));

        customerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}