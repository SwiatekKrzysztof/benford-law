package com.swiatek.benford.graph;

import com.swiatek.benford.commons.BenfordLawValidator;
import com.swiatek.benford.commons.ParsingConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
class GraphCreator {
    BenfordLawValidator benfordLawValidator;

    Mono<Graph> parseDocumentContentToGraph(List<String> documentContent, UUID documentId) {
        return Mono.just(getSearchedColumnIndex(documentContent.get(0).split(ParsingConstants.SEPARATOR.toString())))
                .filter(searchedColumnIndex -> searchedColumnIndex >= 0)
                .flatMap(searchedIndex -> buildGraph(documentId, documentContent, searchedIndex));
    }

    public Mono<Graph> createBenfordGraph(Long sampleSize) {
        return Mono.just(new Graph(null, true, benfordLawValidator.getIdealDigitsMapForSampleSize(sampleSize)));
    }

    private Mono<Graph> buildGraph(UUID documentId, List<String> documentContent, Integer searchedColumnIndex) {
        return Mono.just(extractSearchedColumnFirstDigits(documentContent, searchedColumnIndex))
                .map(this::getDigitsWithCountMap)
                .map(digitsWithCount -> new Graph(documentId,
                        benfordLawValidator.doesDataMatchBenfordLaw(digitsWithCount),
                        digitsWithCount));
    }

    private List<Integer> extractSearchedColumnFirstDigits(List<String> rows, Integer searchedColumnIndex) {
        return rows.stream()
                .filter(row -> !row.contains(ParsingConstants.COLUMN_NAME))
                .map(row -> row.split(ParsingConstants.SEPARATOR.toString()))
                .filter(row -> row.length > searchedColumnIndex)
                .map(row -> row[searchedColumnIndex])
                .map(this::getFirstNonZeroDigit)
                .collect(Collectors.toList());
    }

    private Map<Integer, Long> getDigitsWithCountMap(List<Integer> firstDigits) {
        Map<Integer, Long> digitsWithCount = new HashMap<>();
        firstDigits.forEach(d -> {
            digitsWithCount.putIfAbsent(d, 0L);
            digitsWithCount.put(d, digitsWithCount.get(d) + 1L);
        });
        return digitsWithCount;
    }

    private Integer getSearchedColumnIndex(String[] header) {
        int headerIndex = -1;
        for (int i = 0; i < header.length; i++) {
            if (header[i].trim().replace(ParsingConstants.BOM, "").equals(ParsingConstants.COLUMN_NAME)) {
                headerIndex = i;
                break;
            }
        }
        return headerIndex;
    }

    private Integer getFirstNonZeroDigit(String entry) {
        char[] characters = entry.trim().toCharArray();
        Integer firstNonZeroDigit = null;
        for (char character : characters) {
            if (Character.isDigit(character) && Integer.parseInt(String.valueOf(character)) != 0) {
                firstNonZeroDigit = Integer.parseInt(String.valueOf(character));
                break;
            }
        }
        return firstNonZeroDigit;
    }
}
