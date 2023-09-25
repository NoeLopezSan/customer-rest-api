package dev.noelopez.restdemo1.service;

import dev.noelopez.restdemo1.model.Customer;
import dev.noelopez.restdemo1.repo.CustomerRepo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private CustomerRepo customerRepo ;

    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    List<Customer> findTop5ByStatusOrderByDateOfBirthAsc(Customer.Status status) {
        return customerRepo.findTop5ByStatusOrderByDateOfBirthAsc(status);
    }

    //@Cacheable(cacheNames="customers", key="#customer.name, #customer.status, #customer.details.info, #customer.details.vip")
    public List<Customer> findByFields(Customer customer) {
        return customerRepo.findByFields(customer);
    }
    @Cacheable(cacheNames="customers", key="#id")
    public Optional<Customer> findById(Long id) {
        return customerRepo.findById(id);
    }

    @Transactional
    public Customer save(Customer customer) {
        return customerRepo.save(customer);
    }

    @Transactional
    public void deleteById(Long id) {
        customerRepo.deleteById(id);
    }
}
