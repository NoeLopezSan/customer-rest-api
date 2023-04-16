package dev.noelopez.restdemo1.repo;

import dev.noelopez.restdemo1.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepo extends JpaRepository<Document, Long> {

}
