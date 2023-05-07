package dev.noelopez.restdemo1.controller;

import dev.noelopez.restdemo1.dto.DocumentResponse;
import dev.noelopez.restdemo1.exception.EntityNotFoundException;
import dev.noelopez.restdemo1.exception.FileSizeExceededException;
import dev.noelopez.restdemo1.model.Customer;
import dev.noelopez.restdemo1.model.Document;
import dev.noelopez.restdemo1.repo.DocumentRepo;
import dev.noelopez.restdemo1.util.DocumentUtils;
import dev.noelopez.restdemo1.validation.AllowedExtensions;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static dev.noelopez.restdemo1.util.DocumentUtils.fileSizeExceeded;
//@Validated
@RestController
@RequestMapping("api/v1/documents")
public class DocumentController {
    @Value("${application.rest.v1.url}")
    private String urlEndpointV1;
    private DocumentRepo documentRepo;
    public DocumentController(DocumentRepo documentRepo) {
        this.documentRepo = documentRepo;
    }
    @GetMapping
    public List<DocumentResponse> findDocuments() {
        return documentRepo.findAll()
                .stream()
                .map(DocumentUtils::convertToDocumentResponse)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{documentId}")
    ResponseEntity<Resource> downloadDocument(@PathVariable Long documentId) {
        Document document = documentRepo.findById(documentId)
                .orElseThrow(() -> new EntityNotFoundException(documentId, Document.class));

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(document.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getName() + "\"")
                .body(new ByteArrayResource(document.getContents()));
    }

    @PostMapping()
    ResponseEntity<Void> uploadDocument(@RequestBody @Size(min = 1, max = 1024*1024*1, message = "File Size is larger than 1MB!!") String data,
                                        @RequestHeader("Content-Type") String type,
                                        @RequestHeader("fileName")  @AllowedExtensions String fileName) {

        Document document = new Document();
        document.setName(fileName);
        document.setCreationDate(LocalDate.now());
        document.setCustomerId(2L);
        document.setType(type);
        document.setContents(data.getBytes());

        documentRepo.save(document);

        return ResponseEntity.created(URI.create( urlEndpointV1+"documents/"+document.getId() )).build();
    }
}