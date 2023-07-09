package dev.noelopez.restdemo1.service;

import dev.noelopez.restdemo1.model.Customer;
import dev.noelopez.restdemo1.repo.CustomerRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private CustomerRepo customerRepo ;

    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public List<Customer> findByNameLikeIgnoreCaseOrderByName(String username) {
        return null;
    }
    public List<Customer> findByEmail(String email) {
        return null;
    }
}
