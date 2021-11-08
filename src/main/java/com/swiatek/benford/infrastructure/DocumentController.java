package com.swiatek.benford.infrastructure;

import com.swiatek.benford.document.DocumentFacade;
import com.swiatek.benford.document.UploadedDocument;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.postgresql.api.Notification;
import io.r2dbc.postgresql.api.PostgresqlConnection;
import io.r2dbc.postgresql.api.PostgresqlResult;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RequestMapping("/document")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DocumentController {

    DocumentFacade documentFacade;
    PostgresqlConnectionFactory connectionFactory;

    public DocumentController(DocumentFacade documentFacade, PostgresqlConnectionFactory connectionFactory) {
        this.documentFacade = documentFacade;
        this.connectionFactory = connectionFactory;
    }

    @PostMapping("/list")
    @ResponseBody
    ResponseEntity<Flux<UploadedDocument>> getDocumentsByUuids(@RequestBody List<String> uuids) {
        return ResponseEntity.ok(documentFacade.getUploadedDocumentsByUuids(uuids));
    }

    @GetMapping("/first")
    @ResponseBody
    ResponseEntity<Flux<UploadedDocument>> getDocuments(@RequestParam Integer size) {
        return ResponseEntity.ok(documentFacade.getTopSizeOfUploadedDocuments(size));
    }

    @PostMapping("/next/{currentLargestId}")
    @ResponseBody
    ResponseEntity<Flux<UploadedDocument>> getNextDocuments(@RequestParam Integer size, @PathVariable Long currentLargestId) {
        return ResponseEntity.ok(documentFacade.getNextUploadedDocuments(currentLargestId, size));
    }

    @GetMapping("/list/updates")
    ResponseEntity<Flux<Notification>> documentAdded() {
        return ResponseEntity.ok(connectionFactory.create().flatMapMany(this::documentAdded));
    }

    Flux<Notification> documentAdded(PostgresqlConnection connection) {
        return connection.createStatement("LISTEN document_added_event")
                .execute()
                .flatMap(PostgresqlResult::getRowsUpdated)
                .thenMany(connection.getNotifications());
    }

}
