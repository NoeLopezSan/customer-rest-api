package dev.noelopez.restdemo1.controller;

import dev.noelopez.restdemo1.dto.DocumentResponse;
import dev.noelopez.restdemo1.exception.FileSizeExceededException;
import dev.noelopez.restdemo1.model.Document;
import dev.noelopez.restdemo1.repo.DocumentRepo;
import dev.noelopez.restdemo1.util.DocumentUtils;
import dev.noelopez.restdemo1.validation.AllowedExtensions;
import jakarta.validation.Valid;
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
@Validated
@RestController
@RequestMapping("api/v1/documents")
public class DocumentController {
    private DocumentRepo documentRepo;
    public DocumentController(DocumentRepo documentRepo) {
        this.documentRepo = documentRepo;
    }

    @Value("${application.rest.document.upload.max.size.mb}")
    private int maxUploadSizeInMB;
    @GetMapping
    public List<DocumentResponse> findDocuments() {
        return documentRepo.findAll()
                .stream()
                .map(DocumentUtils::convertToCustomerResponse)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{documentId}")
    ResponseEntity<Resource> downloadDocument(@PathVariable Long documentId) {
//        byte[] data = documentRepo.findById(documentId);
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
//                .getContents();
//      Move to service layer and manage exceptions

        Optional<Document> document = documentRepo.findById(documentId);

        if (document.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(document.get().getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.get().getName() + "\"")
                .body(new ByteArrayResource(document.get().getContents()));
    }

    @PostMapping()
    ResponseEntity<Void> uploadDocument(@RequestBody String data ,@RequestHeader("Content-Type") String type,
                                        @RequestHeader("fileName")  @AllowedExtensions String fileName) {

        if (fileSizeExceeded(maxUploadSizeInMB, data.getBytes())) {
            throw new FileSizeExceededException(data.getBytes().length, maxUploadSizeInMB);
        }

        Document document = new Document();
        document.setName(fileName);
        document.setCreationDate(LocalDate.now());
        document.setCustomerId(2L);
        document.setType(type);
        document.setContents(data.getBytes());

        documentRepo.save(document);

        return ResponseEntity.created(URI.create( "http://localhost:8080/api/v1/documents/"+document.getId() )).build();
    }
}
