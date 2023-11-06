package dev.noelopez.restdemo1.mapper;

import dev.noelopez.restdemo1.dto.DocumentResponse;
import dev.noelopez.restdemo1.model.Document;

public class DocumentMapper {
    public static DocumentResponse convertToDocumentResponse(Document document) {
        return  new DocumentResponse(
                document.getName(),
                document.getCreationDate(),
                document.getContents().length,
                document.getType()
        );
    }
}
