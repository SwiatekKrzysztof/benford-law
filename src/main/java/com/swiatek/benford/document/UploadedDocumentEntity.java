package com.swiatek.benford.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "uploaded_document")
@Getter
@Setter
@NoArgsConstructor
class UploadedDocumentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "document_title")
    private String title;

    @Column(name = "time_added")
    private LocalDateTime timeAdded;

    @Column(name = "content")
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;

    public UploadedDocumentEntity(final String title, final LocalDateTime timeAdded, final byte[] content) {
        this.title = title;
        this.timeAdded = timeAdded;
        this.content = content;
    }

    static UploadedDocumentEntity from(UploadedDocument dto) {
        return new UploadedDocumentEntity(dto.getTitle(), dto.getTimeAdded(), dto.getContent());
    }

    UploadedDocument to() {
        return new UploadedDocument(id, title, timeAdded, content);
    }
}
