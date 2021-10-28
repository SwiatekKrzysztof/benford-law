package com.swiatek.benford.graph;

import com.swiatek.benford.document.events.NewValidDocumentSavedEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class GraphFacade {
    GraphCreator graphCreator;
    GraphRepository graphRepository;

    @EventListener
    @Async
    public void createGraphOnDocumentSaved(NewValidDocumentSavedEvent event) {
        final Optional<Graph> graph = graphCreator.parseDocumentContentToGraph(event.getFileContent(), event.getDocumentId());
        graph.ifPresent(value -> graphRepository.save(GraphEntity.from(value)));
    }


    public Optional<Graph> getGraphForDocumentId(Long documentId) {
        return graphRepository.findByDocumentId(documentId).stream().map(GraphEntity::to).findAny();
    }
}
