package dev.noelopez.restdemo1.repo;

import dev.noelopez.restdemo1.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    public List<Customer> findByNameLikeIgnoreCaseOrderByName(String username);
    public List<Customer> findByEmail(String email);
}
