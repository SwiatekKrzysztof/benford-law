package com.swiatek.benford.integration

import com.swiatek.benford.IntegrationSpec
import com.swiatek.benford.document.DocumentData
import com.swiatek.benford.document.DocumentFacade
import com.swiatek.benford.document.UploadedDocument
import com.swiatek.benford.document.result.UploadResult
import com.swiatek.benford.document.result.ValidationResult
import com.swiatek.benford.graph.Graph
import com.swiatek.benford.graph.GraphFacade
import org.springframework.beans.factory.annotation.Autowired

class DocumentIntegrationSpec extends IntegrationSpec implements DocumentData {

    @Autowired
    DocumentFacade documentFacade

    @Autowired
    GraphFacade graphFacade


    def "Should add document to database and create graph"() {
        given:
        def input = correctInput.getBytes()
        def title = UUID.randomUUID().toString()
        Long generatedId = -1
        when:
        UploadResult uploadResult = documentFacade.uploadDocument(input, title)
        then:
//        conditions.eventually {
        Optional<UploadedDocument> optionalDocument = documentFacade.getUploadedData(uploadResult.id())
        UploadedDocument document = optionalDocument.get()
        document != null
//            generatedId = document.getId()
        document.getContent() == input
        document.getTitle() == title
//        }
        and:
        Optional<Graph> optionalGraph = graphFacade.getGraphForDocumentId(document.getId())
        then:
        optionalGraph.isPresent()
        def map = [2: 1L, 4: 1L]
        //for that small sample, Benford Law is irrelevant
        optionalGraph.get() == new Graph(document.getId(), true, map)
    }

    def "Should not add document to database and emmit event"() {
        when:
        UploadResult uploadResult = documentFacade.uploadDocument(incorrectInput.getBytes(), "Test")
        then:
        ValidationResult.INCORRECT_DATA_FORMAT == uploadResult.validationResult()
    }

}
