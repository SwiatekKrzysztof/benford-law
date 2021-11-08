package com.swiatek.benford.infrastructure;

import com.swiatek.benford.graph.Graph;
import com.swiatek.benford.graph.GraphFacade;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/graph")
@AllArgsConstructor
public class GraphController {

    GraphFacade graphFacade;

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
}
