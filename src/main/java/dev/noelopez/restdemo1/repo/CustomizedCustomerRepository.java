package dev.noelopez.restdemo1.repo;

import dev.noelopez.restdemo1.model.Customer;

import java.util.List;

public interface CustomizedCustomerRepository {

    public List<Customer> indByFields(Customer customer);
}
