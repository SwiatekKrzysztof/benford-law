package com.swiatek.benford.document;

import com.swiatek.benford.graph.GraphFacade;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    public Mono<UUID> uploadDocumentAndAssignUuid(Mono<FilePart> file, String title) {
        return Mono.just(UUID.randomUUID()).doOnNext(uuid -> uploadDocument(file, title, uuid).subscribe());
    }

    public Flux<UploadedDocument> getTopSizeOfUploadedDocuments(Integer size) {
        return documentRepository.findFirstNumberOfDocumentsOrderedByTimeAdded(size)
                .map(UploadedDocumentEntity::to);
    }

    public Flux<UploadedDocument> getNextUploadedDocuments(Long currentLargestId, Integer resultSize) {
        return documentRepository.findNextNumberOfDocumentsOlderThenId(currentLargestId, resultSize)
                .map(UploadedDocumentEntity::to);
    }

    public Flux<UploadedDocument> getUploadedDocumentsByUuids(List<String> uuids) {
        return Flux.just(uuids.stream().filter(Objects::nonNull).map(UUID::fromString).collect(Collectors.toList()))
                .flatMap(documentRepository::findByUuidInOrderByTimeAddedDesc)
                .map(UploadedDocumentEntity::to);
    }

    public Mono<UploadedDocument> getUploadedDocument(UUID uuid) {
        return documentRepository.findByUuid(uuid).map(UploadedDocumentEntity::to);
    }

    public Mono<Resource> getFileByUuid(UUID uuid) {
        return fileService.getFile(uuid)
                .map(FileSystemResource::new);
    }

    Mono<Void> uploadDocument(Mono<FilePart> file, String title, UUID uuid) {
        return contentProcessor.validateAndGetLines(file)
                .switchIfEmpty(Mono.just(List.of()))
                .doOnNext(fileLines -> {
                    if (!fileLines.isEmpty()) {
                        graphFacade.createGraphOnDocumentSaved(fileLines, uuid).subscribe();
                    }
                })
                .map(fileLines -> new UploadedDocumentEntity(title, uuid, LocalDateTime.now(), !fileLines.isEmpty()))
                .flatMap(documentRepository::save)
                .doOnNext(entity -> {
                    if (entity.getValidationPassed()) {
                        fileService.saveFile(file, uuid).subscribe();
                    }
                })
                .then();
    }
}
