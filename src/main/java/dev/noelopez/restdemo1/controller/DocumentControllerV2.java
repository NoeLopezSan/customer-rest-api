package dev.noelopez.restdemo1.controller;

import dev.noelopez.restdemo1.exception.FileSizeExceededException;
import dev.noelopez.restdemo1.model.Document;
import dev.noelopez.restdemo1.repo.DocumentRepo;
import dev.noelopez.restdemo1.util.DocumentUtils;
import dev.noelopez.restdemo1.validation.AllowedExtensions;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;

import static dev.noelopez.restdemo1.util.DocumentUtils.*;
@RestController
@RequestMapping("api/v2/documents")
public class DocumentControllerV2 {
    @Value("${application.rest.v1.url}")
    private String urlEndpointV1;
    private DocumentRepo documentRepo;
    public DocumentControllerV2(DocumentRepo documentRepo) {
        this.documentRepo = documentRepo;
    }
    @PostMapping()
    ResponseEntity<Void> uploadDocument(@RequestBody @Size(min = 1, max = 1024*1024*1, message = "File Size is larger than 1MB!!") String data,
                                        @RequestHeader("Content-Type") String type,
                                        @RequestHeader("fileName")  @AllowedExtensions String fileName) {

        Document document = new Document();
        document.setName(fileName);
        document.setCreationDate(LocalDate.now());
        document.setType(type);
        document.setContents(data.getBytes());

        documentRepo.save(document);

        return ResponseEntity.created(URI.create( urlEndpointV1+"documents/"+document.getId() )).build();
    }
}
