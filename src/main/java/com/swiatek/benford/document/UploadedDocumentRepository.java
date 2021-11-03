package com.swiatek.benford.document;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Repository
interface UploadedDocumentRepository extends ReactiveCrudRepository<UploadedDocumentEntity, Long> {

    @Query("SELECT uuid, title, time_added FROM uploaded_document ORDER BY time_added DESC")
    Flux<UploadedDocumentEntity> findAllTitles();

    Flux<UploadedDocumentEntity> findByUuidInOrderByTimeAddedDesc(List<UUID> uuidList);

    Mono<UploadedDocumentEntity> findByUuid(UUID uuid);
}
