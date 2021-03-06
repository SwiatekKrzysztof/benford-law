package com.swiatek.benford.graph;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
interface GraphRepository extends ReactiveCrudRepository<GraphEntity, Long> {
    Mono<GraphEntity> findByDocumentUuid(UUID uuid);
}
