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

    @Query("SELECT id, uuid, title, time_added, validation_passed FROM uploaded_document WHERE id < :currentLargestId ORDER BY id DESC fetch first :size rows only")
    Flux<UploadedDocumentEntity> findNextNumberOfDocumentsOlderThenId(Long currentLargestId, Integer size);

    @Query("SELECT id, uuid, title, time_added, validation_passed FROM uploaded_document ORDER BY id DESC fetch first :size rows only")
    Flux<UploadedDocumentEntity> findFirstNumberOfDocumentsOrderedByTimeAdded(Integer size);

    Flux<UploadedDocumentEntity> findByUuidInOrderByTimeAddedDesc(List<UUID> uuidList);

    Mono<UploadedDocumentEntity> findByUuid(UUID uuid);
}
