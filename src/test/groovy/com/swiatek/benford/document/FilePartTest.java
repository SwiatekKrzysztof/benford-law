package com.swiatek.benford.document;


import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class FilePartTest implements FilePart {

    static final DataBufferFactory factory = new DefaultDataBufferFactory();
    private final String testContent;

    public FilePartTest(String testContent) {
        this.testContent = testContent;
    }

    @Override
    public @NotNull String filename() {
        return name();
    }

    @Override
    public @NotNull Mono<Void> transferTo(Path dest) {
        return DataBufferUtils.write(content(), dest);
    }

    @Override
    public @NotNull String name() {
        return "name";
    }

    @Override
    public @NotNull HttpHeaders headers() {
        return HttpHeaders.EMPTY;
    }

    @Override
    public @NotNull Flux<DataBuffer> content() {
        return DataBufferUtils.read(
                new ByteArrayResource(testContent.getBytes(StandardCharsets.UTF_8)), factory, 1024);
    }
}
