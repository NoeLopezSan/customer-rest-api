package dev.noelopez.restdemo1.controller;

import dev.noelopez.restdemo1.model.Document;
import dev.noelopez.restdemo1.repo.DocumentRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping("api/v2/documents")
public class DocumentControllerV2 {
    private DocumentRepo documentRepo;
    public DocumentControllerV2(DocumentRepo documentRepo) {
        this.documentRepo = documentRepo;
    }
    @Value("${application.rest.document.upload.max.size.mb}")
    private int maxUploadSizeInMB;

    @PostMapping()
    ResponseEntity<Void> uploadDocument(@RequestBody byte[] data
            ,@RequestHeader("Content-Type") String type
            ,@RequestHeader("fileName") String fileName) {

        if (maxUploadSizeInMB*1024*1024 < data.length) {
            return ResponseEntity.status(HttpStatusCode.valueOf(406)).build();
        }

        Document document = new Document();
        document.setName(fileName);
        document.setCreationDate(LocalDate.now());
        document.setCustomerId(2L);
        document.setType(type);
        document.setContents(data);

        documentRepo.save(document);

        return ResponseEntity.created(URI.create( "http://localhost:8080/api/v1/documents/"+document.getId() )).build();
    }
}
