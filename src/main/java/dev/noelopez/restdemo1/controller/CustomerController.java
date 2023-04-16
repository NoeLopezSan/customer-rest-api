package dev.noelopez.restdemo1.controller;

import dev.noelopez.restdemo1.exception.RestErrorResponse;
import dev.noelopez.restdemo1.exception.CustomerNotFoundException;
import dev.noelopez.restdemo1.util.CustomerUtils;
import dev.noelopez.restdemo1.dto.CustomerRequest;
import dev.noelopez.restdemo1.model.Customer;
import dev.noelopez.restdemo1.repo.CustomerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
    private Logger logger = LoggerFactory.getLogger(CustomerController.class);
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
                //.orElse(new ResponseEntity("Customer "+id+" not found!!" ,HttpStatus.NOT_FOUND));
                //.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer "+id+" not found.!"));
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @PostMapping
    public ResponseEntity<Long> addCustomer(@RequestBody CustomerRequest customerRequest)  {
        Customer customer = CustomerUtils.convertToCustomer(customerRequest);
        customerRepo.save(customer);

        return ResponseEntity.created(URI.create( "http://localhost:8080/api/v1/customers/"+customer.getId() )).build();
    }

    @PutMapping("{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("customerId") Long id, @RequestBody CustomerRequest customerRequest)  {
        Optional<Customer> customer = customerRepo.findById(id);

        if (customer.isEmpty())
            throw new CustomerNotFoundException(id);

        Customer updatedCustomer = CustomerUtils.convertToCustomer(customerRequest);
        updatedCustomer.setId(id);
        customerRepo.save(updatedCustomer);

        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("{customerId}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable("customerId") Long id)  {
        Optional<Customer> customer = customerRepo.findById(id);

        if (customer.isEmpty())
            throw new CustomerNotFoundException(id);;

        customerRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    //    @ExceptionHandler
//    public ResponseEntity<RestErrorResponse> handleException(CustomerNotFoundException ex) {
//        var response = new RestErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now());
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//    }

}