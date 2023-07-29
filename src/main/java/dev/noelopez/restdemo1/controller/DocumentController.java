package dev.noelopez.restdemo1.controller;

import dev.noelopez.restdemo1.dto.DocumentResponse;
import dev.noelopez.restdemo1.exception.EntityNotFoundException;
import dev.noelopez.restdemo1.model.Customer;
import dev.noelopez.restdemo1.model.Document;
import dev.noelopez.restdemo1.repo.DocumentRepo;
import dev.noelopez.restdemo1.service.DocumentService;
import dev.noelopez.restdemo1.util.DocumentUtils;
import dev.noelopez.restdemo1.validation.AllowedExtensions;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static dev.noelopez.restdemo1.util.DocumentUtils.*;
import static dev.noelopez.restdemo1.util.DocumentUtils.fileSizeExceeded;
@Validated
@RestController
@RequestMapping("api/v1/documents")
public class DocumentController {
    Logger logger = LoggerFactory.getLogger(DocumentController.class);
    @Value("${application.rest.v1.url}")
    private String urlEndpointV1;
    private DocumentService documentService;
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }
    @GetMapping
    public List<DocumentResponse> findDocuments(@RequestParam
        @Size(min = 3, max = 100, message = "Name must be have at least {min} characters and no more than {max}.") String name) {
        return documentService.findAll()
                .stream()
                .map(DocumentUtils::convertToDocumentResponse)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{documentId}")
    ResponseEntity<Resource> downloadDocument(@PathVariable
                                              @Positive(message = "Document id must be positive.")
            @Max(value = 99999999, message = "Document id cannot exceed value .")
                                               Long documentId) {
        Document document = documentService.findById(documentId)
                .orElseThrow(() -> new EntityNotFoundException(documentId, Document.class));

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(document.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getName() + "\"")
                .body(new ByteArrayResource(document.getContents()));
    }

    @PostMapping()
    ResponseEntity<Void> uploadDocument(@RequestBody
                                        byte[] data
            ,@RequestHeader("Content-Type") String type
            ,@RequestHeader("fileName") @Pattern(regexp = "^([a-zA-Z0-9_-]{2,200})\\.([a-z]{3,4})$", message = "FileName is invalid.")
                                        @AllowedExtensions String fileName) {
        logger.info("Document size is {}",data);
        Document document = createDocument(data, type, fileName);
        documentService.save(document);

        return ResponseEntity.created(URI.create( urlEndpointV1+"documents/"+document.getId() )).build();
    }

    @DeleteMapping("{documentId}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable("documentId") Long id)  {
        Document document = documentService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Document.class));

        documentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}