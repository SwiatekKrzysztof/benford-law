package com.swiatek.benford.graph;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "graph")
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
class GraphEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    Long id;

    // @OneToOne(targetEntity = UploadedDocumentEntity.class)
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

    public GraphEntity(final Long documentId, final Boolean matchesBenfordLaw, final Long onesCount, final Long twosCount,
                       final Long threesCount, final Long foursCount, final Long fivesCount, final Long sixesCount,
                       final Long sevensCount, final Long eightsCount, final Long ninesCount, final Long errorsCount) {
        this.documentId = documentId;
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
        this.errorsCount = errorsCount;
    }

    public static GraphEntity from(Graph graph) {
        return new GraphEntity(graph.getDocumentId(), graph.getMatchesBenfordLaw(), graph.getOnesCount(), graph.getTwosCount(),
                graph.getThreesCount(), graph.getFoursCount(), graph.getFivesCount(), graph.getSixesCount(),
                graph.getSevensCount(), graph.getEightsCount(), graph.getNinesCount(), graph.getErrorsCount());

    }

    public Graph to() {
        return new Graph(documentId, matchesBenfordLaw, onesCount, twosCount, threesCount, foursCount,
                fivesCount, sixesCount, sevensCount, eightsCount, ninesCount, errorsCount);
    }
}
