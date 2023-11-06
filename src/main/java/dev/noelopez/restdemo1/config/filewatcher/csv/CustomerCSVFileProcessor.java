package dev.noelopez.restdemo1.config.filewatcher.csv;

import dev.noelopez.restdemo1.config.filewatcher.processor.FileProcessor;
import dev.noelopez.restdemo1.mapper.CustomerMapper;
import dev.noelopez.restdemo1.model.Customer;
import dev.noelopez.restdemo1.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Component
public class CustomerCSVFileProcessor implements FileProcessor {
    public static final int NUMBER_OF_COLUMNS = 5;
    public static final String OUTPUT_FOLDER = "\\output";
    private static Logger logger = LoggerFactory.getLogger(CustomerCSVFileProcessor.class);
    private CustomerService customerService;
    public CustomerCSVFileProcessor(CustomerService customerService) {
        this.customerService = customerService;
    }
    public void process(Path file) {
        logger.info(String.format("Init processing file %s",file.getFileName()));
        var parser = CSVParser.parse(file);
        parser.getRecords().forEach(this::processRecord);
        moveFile(file);
    }

    private void processRecord(CSVRecord csvRecord) {
        if (csvRecord.size() < NUMBER_OF_COLUMNS) {
            logger.info(String.format("Line %d skipped. Not enough values.", csvRecord.lineNumber()));
            return;
        }

        Customer customer = CustomerMapper.mapToCustomer(csvRecord);
        customer.setStatus(Customer.Status.ACTIVATED);
        customerService.save(customer);
        logger.info(String.format("Saved customer %s in line %d",customer.getName(), csvRecord.lineNumber()));
    }

    private static void moveFile(Path file) {
        try {
            var destinationFolder = Path.of(file.getParent().toString() + OUTPUT_FOLDER );
            Files.move(file, destinationFolder.resolve(file.getFileName()), REPLACE_EXISTING);
            logger.info(String.format("File %s has been moved to %s",file.getFileName(), destinationFolder));
        } catch (IOException e) {
            logger.error("Unable to move file "+ file, e);
        }
    }
}
