package com.swiatek.benford.document;

import com.swiatek.benford.document.result.UploadResult;
import com.swiatek.benford.document.result.ValidationResult;
import com.swiatek.benford.graph.GraphFacade;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DocumentFacade {
    DocumentContentProcessor contentProcessor;
    DocumentFileService fileService;
    UploadedDocumentRepository documentRepository;
    GraphFacade graphFacade;

    public Mono<UploadResult> uploadDocument(Mono<FilePart> file, String title, UUID uuid) {
        return contentProcessor.validateAndGetLines(file)
                .doOnNext(fileLines -> saveDocumentAndStartGraphCreation(file, fileLines, title, uuid).subscribe())
                .flatMap(fileLines -> Mono.just(new UploadResult(uuid, ValidationResult.SUCCESS)))
                .switchIfEmpty(Mono.just(new UploadResult(null, ValidationResult.INCORRECT_DATA_FORMAT)));
    }

    public Flux<UploadedDocument> getUploadedDocuments() {
        return documentRepository.findAllTitles()
                .map(UploadedDocumentEntity::to);
    }

    public Flux<UploadedDocument> getUploadedDocumentsByUuids(List<String> uuids) {
        return Flux.just(uuids.stream().filter(Objects::nonNull).map(UUID::fromString).collect(Collectors.toList()))
                .flatMap(documentRepository::findByUuidInOrderByTimeAddedDesc)
                .map(UploadedDocumentEntity::to);
    }

    Mono<Void> saveDocumentAndStartGraphCreation(Mono<FilePart> fileMono, List<String> fileLines, String title, UUID uuid) {
        return fileMono
//                .delayElement(Duration.ofMillis(10000))
                .doOnNext(file -> fileService.saveFile(file, uuid).subscribe())
                .map(ignore -> new UploadedDocumentEntity(title, uuid, LocalDateTime.now()))
                .flatMap(documentRepository::save)
                .flatMap(savedEntity -> graphFacade.createGraphOnDocumentSaved(fileLines, uuid))
                .then();
    }

    public Mono<UploadedDocument> getUploadedData(UUID uuid) {
        return documentRepository.findByUuid(uuid).map(UploadedDocumentEntity::to);
    }

    public Mono<File> getFileContentByUuid(UUID uuid) {
        return fileService.getFile(uuid);
    }

}
