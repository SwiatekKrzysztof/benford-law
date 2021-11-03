package com.swiatek.benford.infrastructure;

import com.swiatek.benford.graph.Graph;
import com.swiatek.benford.graph.GraphFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/graph")
@AllArgsConstructor
public class GraphController {

    GraphFacade graphFacade;

    @GetMapping(value = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<Graph> getGraphs() {
        return graphFacade.getAllGraphs();
    }

    @GetMapping("/{uuid}")
    @ResponseBody
    public Mono<Graph> getGraph(@PathVariable String uuid) {
        return graphFacade.getGraphForDocumentId(UUID.fromString(uuid));
    }

    @GetMapping("/ideal/{sampleSize}")
    @ResponseBody
    public Mono<Graph> getIdealGraphForSampleSize(@PathVariable Long sampleSize) {
        return graphFacade.getIdealBenfordGraph(sampleSize);
    }

    @AllArgsConstructor
    static class GraphPair {
        Graph realGraph;
        Graph idealGraph;
    }
}
