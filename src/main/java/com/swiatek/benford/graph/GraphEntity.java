package com.swiatek.benford.graph;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("graph")
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
class GraphEntity {
    @Id
    Long id;

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

    Long errorCount;

    public GraphEntity(UUID documentUuid, Boolean matchesBenfordLaw, Long onesCount, Long twosCount,
                       Long threesCount, Long foursCount, Long fivesCount, Long sixesCount,
                       Long sevensCount, Long eightsCount, Long ninesCount, Long errorCount) {
        this.documentUuid = documentUuid;
        this.matchesBenfordLaw = matchesBenfordLaw;
        this.onesCount = onesCount;
        this.twosCount = twosCount;
        this.threesCount = threesCount;
        this.foursCount = foursCount;
        this.fivesCount = fivesCount;
        this.sixesCount = sixesCount;
        this.sevensCount = sevensCount;
        this.eightsCount = eightsCount;
        this.ninesCount = ninesCount;
        this.errorCount = errorCount;
    }

    public static GraphEntity from(Graph graph) {
        return new GraphEntity(graph.getDocumentUuid(), graph.getMatchesBenfordLaw(), graph.getOnesCount(), graph.getTwosCount(),
                graph.getThreesCount(), graph.getFoursCount(), graph.getFivesCount(), graph.getSixesCount(),
                graph.getSevensCount(), graph.getEightsCount(), graph.getNinesCount(), graph.getErrorCount());

    }

    public Graph to() {
        return new Graph(documentUuid, matchesBenfordLaw, onesCount, twosCount, threesCount, foursCount,
                fivesCount, sixesCount, sevensCount, eightsCount, ninesCount, errorCount);
    }
}
