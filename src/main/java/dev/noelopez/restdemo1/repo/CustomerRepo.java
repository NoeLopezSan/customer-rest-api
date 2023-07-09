package dev.noelopez.restdemo1.repo;

import dev.noelopez.restdemo1.model.Customer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer, Long> , CustomizedCustomerRepository {

    public List<Customer> findTop5ByStatusOrderByDateOfBirthAsc(Customer.Status status);
    @Query("""
        SELECT c FROM Customer c 
        JOIN FETCH c.details d
        WHERE (c.status = :status or :status is null) 
          and (c.name = :name or :name is null)
        """)
    public List<Customer> findCustomerByStatusAndName(
            @Param("status") Customer.Status status,
            @Param("name") String name);
}
