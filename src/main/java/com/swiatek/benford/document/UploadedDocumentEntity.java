package com.swiatek.benford.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("uploaded_document")
@Data
@NoArgsConstructor
class UploadedDocumentEntity {

    @Id
    private Long id;

    private UUID uuid;

    private String title;

    private LocalDateTime timeAdded;

    private Boolean validationPassed;

    public UploadedDocumentEntity(String title, UUID uuid, LocalDateTime timeAdded, Boolean validationPassed) {
        this.title = title;
        this.uuid = uuid;
        this.timeAdded = timeAdded;
        this.validationPassed = validationPassed;
    }

    UploadedDocument to() {
        return new UploadedDocument(id, uuid, title, timeAdded, validationPassed);
    }
}
