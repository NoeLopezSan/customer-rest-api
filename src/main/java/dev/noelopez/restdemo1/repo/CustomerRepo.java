package dev.noelopez.restdemo1.repo;

import dev.noelopez.restdemo1.model.Customer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    public List<Customer> findByNameLikeIgnoreCaseOrderByName(String username);
    public List<Customer> findByEmail(String email);

    public List<Customer> findByStatus(Integer status);

    @Query("SELECT c FROM Customer c WHERE c.status = :status and c.name = :name")
    public Customer findCustomerByStatusAndNameNamedParams(@Param("status") Integer status, @Param("name") String name);
}
