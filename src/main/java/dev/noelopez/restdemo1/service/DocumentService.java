package dev.noelopez.restdemo1.service;

import dev.noelopez.restdemo1.model.Customer;
import dev.noelopez.restdemo1.model.Document;
import dev.noelopez.restdemo1.repo.DocumentRepo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {
    private DocumentRepo documentRepo;
    public DocumentService(DocumentRepo documentRepo) {
        this.documentRepo = documentRepo;
    }
    public List<Document> findAll() {
        return documentRepo.findAll(Sort.by(Sort.Order.by("id")));
    }
    public Optional<Document> findById(Long id) {
        return documentRepo.findById(id);
    }

    @Transactional
    public Document save(Document document) {
        return documentRepo.save(document);
    }

    @Transactional
    public void deleteById(Long id) {
        documentRepo.deleteById(id);
    }
}
