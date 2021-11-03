package com.swiatek.benford.graph;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Map;
import java.util.UUID;

@Value
@AllArgsConstructor
public class Graph {
    UUID documentUuid;
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

    Long getTotalCount() {
        return onesCount+twosCount+threesCount+foursCount+fivesCount+sixesCount+sevensCount+eightsCount+ninesCount;
    }

    public Graph(UUID documentUuid, boolean matchesBenfordLaw, Map<Integer, Long> digitsWithCount) {
        this.documentUuid = documentUuid;
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
    }
}
