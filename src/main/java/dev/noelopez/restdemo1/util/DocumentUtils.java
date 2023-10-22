package dev.noelopez.restdemo1.util;

import dev.noelopez.restdemo1.dto.DocumentResponse;
import dev.noelopez.restdemo1.model.Document;

import java.time.LocalDate;

public class DocumentUtils {
//    public static Document convertToCustomer(CustomerRequest customerRequest) {
//        Customer customer = new Customer();
//        customer.setName(customerRequest.name());
//        customer.setEmail(customerRequest.email());
//        customer.setDateOfBirth(customerRequest.dateOfBirth());
//        return customer;
//    }

    public static Document createDocument(byte[] data, String type, String fileName) {
        Document document = new Document();
        document.setName(fileName);
        document.setCreationDate(LocalDate.now());
        document.setType(type);
        document.setContents(data);
        return document;
    }

    public static Document createDocument(String data, String type, String fileName) {
        Document document = new Document();
        document.setName(fileName);
        document.setCreationDate(LocalDate.now());
        document.setType(type);
        document.setContents(data.getBytes());
        return document;
    }
    public static DocumentResponse convertToDocumentResponse(Document document) {
        return  new DocumentResponse(
                document.getName(),
                document.getCreationDate(),
                document.getContents().length,
                document.getType()
        );
    }

    public static boolean fileSizeExceeded(int maxUploadSizeInMB, byte[] data) {
        return maxUploadSizeInMB * 1024 * 1024 < data.length;
    }
}
