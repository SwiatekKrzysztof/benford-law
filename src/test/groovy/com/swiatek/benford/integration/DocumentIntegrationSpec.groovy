package com.swiatek.benford.integration

import com.swiatek.benford.IntegrationSpec
import com.swiatek.benford.document.DocumentData
import com.swiatek.benford.document.DocumentFacade
import com.swiatek.benford.document.FilePartTest
import com.swiatek.benford.document.UploadedDocument
import com.swiatek.benford.document.result.UploadResult
import com.swiatek.benford.document.result.ValidationResult
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
        def uuid = UUID.randomUUID()
        when:
        UploadResult uploadResult = documentFacade.uploadDocument(Mono.just(input), title, uuid).block()
        then:
        UploadedDocument document
        conditions.eventually {
            document = documentFacade.getUploadedData(uploadResult.uuid()).block()
            document != null
            document.getTitle() == title
        }
        and:
        Mono<Graph> graphMono = graphFacade.getGraphForDocumentId(document.getUuid())
        then:
        def graph = graphMono.block()
        def map = [2: 1L, 4: 1L]
        //for that small sample, Benford Law is irrelevant
        graph == new Graph(document.getUuid(), true, map)
    }

    def "Should not add document to database and emmit event"() {
        given:
        var uuid = UUID.randomUUID()
        when:
        def uploadResult = documentFacade.uploadDocument(Mono.just(new FilePartTest(incorrectInput)), "Test", uuid).block()
        then:
        uploadResult.validationResult() == ValidationResult.INCORRECT_DATA_FORMAT
        !documentFacade.getUploadedData(uuid).block()
    }

}
