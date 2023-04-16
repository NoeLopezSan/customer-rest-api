package dev.noelopez.restdemo1.util;

import dev.noelopez.restdemo1.dto.DocumentResponse;
import dev.noelopez.restdemo1.model.Document;

public class DocumentUtils {
//    public static Document convertToCustomer(CustomerRequest customerRequest) {
//        Customer customer = new Customer();
//        customer.setName(customerRequest.name());
//        customer.setEmail(customerRequest.email());
//        customer.setDateOfBirth(customerRequest.dateOfBirth());
//        return customer;
//    }

    public static DocumentResponse convertToCustomerResponse(Document document) {
        return  new DocumentResponse(
                document.getName(),
                document.getCreationDate(),
                document.getContents().length,
                document.getType()
        );
    }
}
