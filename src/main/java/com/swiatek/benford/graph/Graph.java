package com.swiatek.benford.graph;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Map;

@Value
@AllArgsConstructor
public class Graph {
    Long documentId;
    Boolean matchesBenfordLaw;

    Long onesCount;
    Long twosCount;
    Long threesCount;
    Long foursCount;
    Long fivesCount;
    Long sixesCount;
    Long sevensCount;
    Long eightsCount;
    Long ninesCount;

    Long errorsCount;

    public Graph(final Long documentId, boolean matchesBenfordLaw, Map<Integer, Long> digitsWithCount) {
        this.documentId = documentId;
        this.matchesBenfordLaw = matchesBenfordLaw;
        this.onesCount = digitsWithCount.get(1);
        this.twosCount = digitsWithCount.get(2);
        this.threesCount = digitsWithCount.get(3);
        this.foursCount = digitsWithCount.get(4);
        this.fivesCount = digitsWithCount.get(5);
        this.sixesCount = digitsWithCount.get(6);
        this.sevensCount = digitsWithCount.get(7);
        this.eightsCount = digitsWithCount.get(8);
        this.ninesCount = digitsWithCount.get(9);
        this.errorsCount = digitsWithCount.get(null);
    }
}
