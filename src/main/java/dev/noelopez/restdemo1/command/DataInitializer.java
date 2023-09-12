package dev.noelopez.restdemo1.command;

import dev.noelopez.restdemo1.model.Customer;
import dev.noelopez.restdemo1.model.CustomerDetails;
import dev.noelopez.restdemo1.model.Document;
import dev.noelopez.restdemo1.repo.CustomerRepo;
import dev.noelopez.restdemo1.repo.DocumentRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@Configuration
public class DataInitializer implements CommandLineRunner {

    private static final byte[] fileContents;

    static {
        try {
            fileContents = Files.readAllBytes(Paths.get("C:\\workspace\\files\\DownloadedFile.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private CustomerRepo customerRepo;
    private DocumentRepo documentRepo;
    public DataInitializer(CustomerRepo customerRepo, DocumentRepo documentRepo) {
        this.customerRepo = customerRepo;
        this.documentRepo = documentRepo;
    }

    @Override
    public void run(String... args) {
        IntStream.rangeClosed(1,50)
                .forEach(this::createRandomCustomer);

        IntStream.rangeClosed(1,50)
                .forEach(this::createRandomDocument);
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

    public void createRandomDocument(int id) {
        Document document = new Document();
        document.setId((long)id);
        document.setCustomerId((long)id);
        document.setName("Document number "+id);
        document.setCreationDate(LocalDate.now());
        document.setType("txt");
        document.setContents(fileContents);

        documentRepo.save(document);
    }

    private static LocalDate getRandomLocalDate(int id) {
        return LocalDate.of(1970 + id%40,(id % 11)+1,1 + (3 * id) % 28);
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
