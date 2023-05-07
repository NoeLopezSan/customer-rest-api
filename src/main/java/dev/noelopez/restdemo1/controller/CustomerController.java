package dev.noelopez.restdemo1.controller;

import dev.noelopez.restdemo1.exception.EntityNotFoundException;
import dev.noelopez.restdemo1.util.CustomerUtils;
import dev.noelopez.restdemo1.dto.CustomerRequest;
import dev.noelopez.restdemo1.model.Customer;
import dev.noelopez.restdemo1.repo.CustomerRepo;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
    private Logger logger = LoggerFactory.getLogger(CustomerController.class);
    @Value("${application.rest.v1.url}")
    private String urlEndpointV1;
    private CustomerRepo customerRepo;
    public CustomerController(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }
    @GetMapping
    public List<Customer> findCustomers() {
        return customerRepo.findAll();
    }

    @GetMapping("{customerId}")
    public ResponseEntity<Customer> findCustomers(@PathVariable("customerId") Long id) {
        return customerRepo
                .findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException(id, Customer.class));
    }

    @PostMapping
    public ResponseEntity<Long> addCustomer(@Valid @RequestBody CustomerRequest customerRequest)  {
        Customer customer = CustomerUtils.convertToCustomer(customerRequest);
        customerRepo.save(customer);

        return ResponseEntity.created(URI.create(urlEndpointV1+"customers/"+customer.getId() )).build();
    }

    @PutMapping("{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("customerId") Long id, @Valid @RequestBody CustomerRequest customerRequest)  {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Customer.class));

        Customer updatedCustomer = CustomerUtils.convertToCustomer(customerRequest);
        updatedCustomer.setId(id);
        customerRepo.save(updatedCustomer);

        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("{customerId}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable("customerId") Long id)  {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Customer.class));

        customerRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}