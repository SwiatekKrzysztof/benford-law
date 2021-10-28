package com.swiatek.benford.graph

import com.swiatek.benford.commons.BenfordLawValidator
import spock.lang.Specification

import java.util.stream.Collectors

class GraphSpec extends Specification implements GraphData {

    BenfordLawValidator benfordLawValidator = new BenfordLawValidator()
    GraphCreator graphCreator = new GraphCreator(benfordLawValidator)

    def "Should extract proper values from dataset"() {
        when: "Data is correct"
        List<Integer> data = graphCreator.extractSearchedColumnData(getRows(correctInputNoHeader), 2)
        then: "Return all digits"
        data == [2, 4]
        when: "Entry contains only non-digit characters"
        data = graphCreator.extractSearchedColumnData(getRows(inputWithSomeIncorrectValuesNoHeader), 2)
        then: "Row is saved as null"
        data == [null, 7]
        when: "Entry starts with zero"
        data = graphCreator.extractSearchedColumnData(getRows(correctInputNoHeaderWithZeroEntry), 2)
        then: "Should search for next non-zero digit"
        data == [9, 4]
    }


    static List<String[]> getRows(String dataset) {
        dataset.lines().map(row -> row.split("\t")).collect(Collectors.toList())
    }
}
