package com.swiatek.benford.document

import com.swiatek.benford.document.result.ValidationResult
import spock.lang.Specification

class ValidatorSpec extends Specification implements DocumentData {


    void setup() {
    }

    def "Should parse correct file"() {
        given:
        DocumentContentValidator contentValidator = new DocumentContentValidator()
        expect:
        ValidationResult.SUCCESS == contentValidator.validateDocument(correctInput.getBytes())
        ValidationResult.SUCCESS == contentValidator.validateDocument(correctOneColumn.getBytes())
        ValidationResult.SUCCESS == contentValidator.validateDocument(correctInputWithBOM.getBytes())
    }

    def "Should validate line as correct"() {
        given:
        DocumentContentValidator contentValidator = new DocumentContentValidator()
        expect:
        ValidationResult.INCORRECT_DATA_FORMAT == contentValidator.validateDocument(incorrectInput.getBytes())
    }


}
