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

    public UploadedDocumentEntity(final String title, UUID uuid, final LocalDateTime timeAdded) {
        this.title = title;
        this.uuid = uuid;
        this.timeAdded = timeAdded;
    }

    UploadedDocument to() {
        return new UploadedDocument(id, uuid, title, timeAdded);
    }
}
