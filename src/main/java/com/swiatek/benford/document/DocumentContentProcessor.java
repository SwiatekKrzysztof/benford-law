package com.swiatek.benford.document;

import com.swiatek.benford.commons.ParsingConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
class DocumentContentProcessor {

    Mono<List<String>> validateAndGetLines(Mono<FilePart> file) {
        return file.flatMapMany(filePart ->
                        filePart.content().map(dataBuffer -> {
                                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                                    dataBuffer.read(bytes);
                                    DataBufferUtils.release(dataBuffer);
                                    return new String(bytes, StandardCharsets.UTF_8);
                                })
                                .map(this::processAndGetLinesAsList))
                .flatMapIterable(Function.identity())
                .collectList()
                .filter(list -> doesDocumentHaveCorrectColumnInHeader(list.get(0)));
    }

    private List<String> processAndGetLinesAsList(String string) {
        return string.lines()
                .filter(s -> !s.isBlank())
                .collect(Collectors.toList());
    }

    private boolean doesDocumentHaveCorrectColumnInHeader(String firstLine) {
        return Arrays.stream(firstLine.split(ParsingConstants.SEPARATOR.toString()))
                .sequential()
                .map(String::trim)
                .map(s -> s.replace(ParsingConstants.BOM, ""))
                .collect(Collectors.toList())
                .contains(ParsingConstants.COLUMN_NAME);
    }
}
