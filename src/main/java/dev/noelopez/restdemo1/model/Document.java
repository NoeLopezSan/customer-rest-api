package dev.noelopez.restdemo1.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "Document")
public class Document {
    public static Document createDocument(byte[] data, String type, String fileName) {
        Document document = new Document();
        document.setName(fileName);
        document.setCreationDate(LocalDate.now());
        document.setType(type);
        document.setContents(data);
        return document;
    }
    @Id
    @SequenceGenerator(name = "document_id_sequence", sequenceName = "document_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "document_id_sequence")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Customer customer;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
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
        return Objects.equals(id, document.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
