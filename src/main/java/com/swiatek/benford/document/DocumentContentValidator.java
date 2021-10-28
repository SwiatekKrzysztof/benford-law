package com.swiatek.benford.document;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.swiatek.benford.commons.ParsingConstants;
import com.swiatek.benford.document.result.ValidationResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Service
class DocumentContentValidator {
    ValidationResult validateDocument(byte[] fileContent) {
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(fileContent)))) {
            final CSVParser parser = new CSVParserBuilder()
                    .withSeparator(ParsingConstants.SEPARATOR)
                    .withIgnoreQuotations(true)
                    .build();
            final CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withCSVParser(parser)
                    .build();
            final String[] strings = csvReader.readNext();
            if (doesDocumentHaveCorrectColumnInHeader(strings)) {
                return ValidationResult.SUCCESS;
            }
        } catch (IOException | CsvValidationException e) {
            log.debug("File incorrect", e);
        }
        return ValidationResult.INCORRECT_DATA_FORMAT;
    }

    private boolean doesDocumentHaveCorrectColumnInHeader(String[] firstLine) {
        return Arrays.stream(firstLine)
                .map(String::trim)
                .map(s -> s.replace(ParsingConstants.BOM, ""))
                .collect(Collectors.toList())
                .contains(ParsingConstants.COLUMN_NAME);
    }
}
