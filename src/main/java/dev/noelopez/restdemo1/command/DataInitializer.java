package dev.noelopez.restdemo1.command;

import dev.noelopez.restdemo1.model.Customer;
import dev.noelopez.restdemo1.repo.CustomerRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.IntStream;

@Configuration
public class DataInitializer implements CommandLineRunner {

    private CustomerRepo customerRepo;

    public DataInitializer(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public void run(String... args) {
        List<Customer> customers = customerRepo.findByNameLikeIgnoreCaseOrderByName("%joe%");
        customers.forEach(System.out::println);

        //IntStream.rangeClosed(1,10).forEach();

    }
}
