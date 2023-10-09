package dev.noelopez.restdemo1.service;

import dev.noelopez.restdemo1.model.Customer;
import dev.noelopez.restdemo1.repo.CustomerRepo;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
//@CacheConfig(cacheNames = "customers")
public class CustomerService {

    private CustomerRepo customerRepo ;

    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    List<Customer> findTop5ByStatusOrderByDateOfBirthAsc(Customer.Status status) {
        return customerRepo.findTop5ByStatusOrderByDateOfBirthAsc(status);
    }

    @Cacheable(cacheNames="customersSearch",
        key= "{#customer.name, #customer.status, #customer.email, #customer.details.vip}",
        condition="#customer.name?.length() < 10",
        unless ="#result.size() < 10"
    )
    public List<Customer> findByFields(Customer customer) {
        return customerRepo.findByFields(customer);
    }
    @Cacheable(cacheNames="customers", key="#id")
    //@Cacheable(cacheNames="customers", key="#id", unless = "not #result?.details.vip")
    public Optional<Customer> findById(Long id) {
        return customerRepo.findById(id);
    }

    @Transactional
    @CachePut(cacheNames="customers", key="#customer.id")
    @CacheEvict(cacheNames="customersSearch", allEntries=true)
    public Customer save(Customer customer) {
        return customerRepo.save(customer);
    }

    @Transactional
    //@CacheEvict(cacheNames="customers", key = "#id")
    @Caching(evict = {
            @CacheEvict(cacheNames="customers", key = "#id"),
            @CacheEvict(cacheNames="customersSearch", allEntries=true)
    })
    public void deleteById(Long id) {
        customerRepo.deleteById(id);
    }
}
