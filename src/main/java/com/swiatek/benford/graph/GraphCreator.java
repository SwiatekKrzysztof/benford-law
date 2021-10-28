package com.swiatek.benford.graph;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.swiatek.benford.commons.BenfordLawValidator;
import com.swiatek.benford.commons.ParsingConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
class GraphCreator {
    BenfordLawValidator benfordLawValidator;

    Optional<Graph> parseDocumentContentToGraph(byte[] documentContent, Long documentId) {
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(documentContent)))) {
            var parser = new CSVParserBuilder()
                    .withSeparator(ParsingConstants.SEPARATOR)
                    .withIgnoreQuotations(true)
                    .build();
            var csvReader = new CSVReaderBuilder(reader)
                    .withCSVParser(parser)
                    .build();
            Integer searchedColumnIndex = getSearchedColumnIndex(csvReader.readNext());
            if (searchedColumnIndex >= 0) {
                return buildGraph(documentId, csvReader.readAll(), searchedColumnIndex);
            }
        } catch (IOException | CsvException e) {
            log.debug("File incorrect", e);
        }

        return Optional.empty();
    }

    private Optional<Graph> buildGraph(Long documentId, List<String[]> documentContent, Integer searchedColumnIndex) {
        var firstDigits = extractSearchedColumnFirstDigits(documentContent, searchedColumnIndex);
        var digitsWithCount = getDigitsWithCountMap(firstDigits);
        boolean matchesBenfordLaw = benfordLawValidator.doesDataMatchBenfordLaw(digitsWithCount);
        return Optional.of(new Graph(documentId, matchesBenfordLaw, digitsWithCount));
    }

    private List<Integer> extractSearchedColumnFirstDigits(List<String[]> rows, Integer searchedColumnIndex) {
        return rows.stream()
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
