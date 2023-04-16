package dev.noelopez.restdemo1.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "Document")
public class Document {
    @Id
    @SequenceGenerator(name = "document_id_sequence", sequenceName = "document_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "document_id_sequence")
    private Long id;
    private Long customerId;
    private String name;
    @Lob
    private byte[] contents;
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContents() {
        return contents;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    private LocalDate creationDate;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public Document() { }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", name='" + name + '\'' +
                ", contents=" + Arrays.toString(contents) +
                ", type='" + type + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return Objects.equals(id, document.id) && Objects.equals(customerId, document.customerId) && Objects.equals(name, document.name) && Arrays.equals(contents, document.contents) && Objects.equals(type, document.type) && Objects.equals(creationDate, document.creationDate);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, customerId, name, type, creationDate);
        result = 31 * result + Arrays.hashCode(contents);
        return result;
    }
}
