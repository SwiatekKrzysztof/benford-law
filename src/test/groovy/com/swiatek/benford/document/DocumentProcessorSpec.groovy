package com.swiatek.benford.document


import reactor.core.publisher.Mono
import spock.lang.Specification

class DocumentProcessorSpec extends Specification implements DocumentData {

    DocumentContentProcessor contentProcessor

    void setup() {
        contentProcessor = new DocumentContentProcessor()
    }

    def "Should parse correct file"() {
        expect:
        contentProcessor.validateAndGetLines(Mono.just(new FilePartTest(correctInput))).block()
        contentProcessor.validateAndGetLines(Mono.just(new FilePartTest(correctOneColumn))).block()
        contentProcessor.validateAndGetLines(Mono.just(new FilePartTest(correctInputWithBOM))).block()
        !contentProcessor.validateAndGetLines(Mono.just(new FilePartTest(incorrectInput))).block()
    }

    def "Should validate file as incorrect"() {
        expect:
        !contentProcessor.validateAndGetLines(Mono.just(new FilePartTest(incorrectInput))).block()
    }


}
