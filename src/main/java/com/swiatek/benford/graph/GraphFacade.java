package com.swiatek.benford.graph;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class GraphFacade {
    GraphCreator graphCreator;
    GraphRepository graphRepository;


    public Mono<Void> createGraphOnDocumentSaved(List<String> fileContent, UUID documentUUID) {
        return graphCreator.parseDocumentContentToGraph(fileContent, documentUUID)
                .flatMap(graph -> graphRepository.save(GraphEntity.from(graph)))
                .then();
    }


    public Mono<Graph> getGraphForDocumentId(UUID documentUuid) {
        return graphRepository.findByDocumentUuid(documentUuid).map(GraphEntity::to);
    }

    public Mono<Graph> getIdealBenfordGraph(Long sampleSize) {
        return graphCreator.createBenfordGraph(sampleSize);
    }
}
