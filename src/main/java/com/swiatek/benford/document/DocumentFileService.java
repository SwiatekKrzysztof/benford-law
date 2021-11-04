package com.swiatek.benford.document;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;
import java.nio.file.Path;
import java.util.UUID;

@Service
@AllArgsConstructor
@PropertySource("classpath:application.properties")
public class DocumentFileService {

    public static final String DOCUMENT_SAVE_DIR_DEFAULT = "files/";

    Environment env;

    Mono<File> getFile(UUID uuid) {
        return Mono.just(pathFromUuid(uuid).toFile());
    }

    Mono<Void> saveFile(FilePart filePart, UUID uuid) {
        return filePart.transferTo(pathFromUuid(uuid));
    }

    private Path pathFromUuid(UUID uuid) {
        return Path.of(env.getProperty("document.save.path", DOCUMENT_SAVE_DIR_DEFAULT), uuid.toString());
    }

}
