package com.swiatek.benford.integration

import com.swiatek.benford.IntegrationSpec
import com.swiatek.benford.document.DocumentData
import com.swiatek.benford.document.DocumentFacade
import com.swiatek.benford.document.FilePartTest
import com.swiatek.benford.document.UploadedDocument
import com.swiatek.benford.graph.Graph
import com.swiatek.benford.graph.GraphFacade
import org.springframework.beans.factory.annotation.Autowired
import reactor.core.publisher.Mono

class DocumentIntegrationSpec extends IntegrationSpec implements DocumentData {

    @Autowired
    DocumentFacade documentFacade

    @Autowired
    GraphFacade graphFacade


    def "Should add document to database and create graph"() {
        given:
        def input = new FilePartTest(correctInput)
        def title = UUID.randomUUID().toString()
        when:
        def uuid = documentFacade.uploadDocumentAndAssignUuid(Mono.just(input), title).block()
        then:
        UploadedDocument document
        conditions.eventually {
            document = documentFacade.getUploadedDocument(uuid).block()
            document != null
            document.getTitle() == title
        }
        and:
        Mono<Graph> graphMono = graphFacade.getGraphForDocumentId(uuid)
        then:
        def graph = graphMono.block()
        def map = [2: 1L, 4: 1L]
        //for that small sample, Benford Law is irrelevant
        graph == new Graph(uuid, true, map)
    }

    def "Should add document"() {
        when:
        def uuid = documentFacade.uploadDocumentAndAssignUuid(Mono.just(new FilePartTest(incorrectInput)), "Test").block()
        then:
        UploadedDocument document;
        conditions.eventually {
            document = documentFacade.getUploadedDocument(uuid).block()
        }
        !document.getValidationPassed()
    }

}
