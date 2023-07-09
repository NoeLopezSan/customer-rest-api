package dev.noelopez.restdemo1.command;

import dev.noelopez.restdemo1.model.Customer;
import dev.noelopez.restdemo1.model.CustomerDetails;
import dev.noelopez.restdemo1.repo.CustomerRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
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
        IntStream.rangeClosed(1,9)
                .forEach(this::createRandomCustomer);

        customerRepo.findById(1L);

        customerRepo.findTop5ByStatusOrderByDateOfBirthAsc(Customer.Status.ACTIVATED);

        Customer c = Customer.Builder
                .newCustomer()
                .name("John")
                .status(Customer.Status.ACTIVATED)
                .withDetails("info",false)
                .build();
        List<Customer> customers = customerRepo.findByAllFields(c);
        System.out.println(customers);
    }
    public void createRandomCustomer(int id) {
        Customer customer = Customer.Builder
                .newCustomer()
                .id((long)id)
                .name("name "+id+" surname "+id)
                .email("organisation"+id+"@email.com")
                .dob(getRandomLocalDate(id))
                .status(getRandomStatus(id))
                .build();
        customer.setDetails(new CustomerDetails("Customer info details "+id, id%2 == 0));

        customerRepo.save(customer);
    }

    private static LocalDate getRandomLocalDate(int id) {
        return LocalDate.of(1980 + 2 * id, id % 12, (3 * id) % 28);
    }

    private static Customer.Status getRandomStatus(int id) {
        return switch (id % 3) {
            case 0 -> Customer.Status.ACTIVATED;
            case 1 -> Customer.Status.DEACTIVATED;
            case 2 -> Customer.Status.SUSPENDED;
            default -> throw new IllegalStateException("Unexpected value: " + id % 3);
        };
    }
}
