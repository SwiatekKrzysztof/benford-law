package com.swiatek.benford.document;

import com.swiatek.benford.document.events.NewValidDocumentSavedEvent;
import com.swiatek.benford.document.result.UploadResult;
import com.swiatek.benford.document.result.ValidationResult;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DocumentFacade {
    DocumentContentValidator contentValidator;
    UploadedDocumentRepository documentRepository;
    ApplicationEventPublisher publisher;

    public UploadResult uploadDocument(byte[] fileContent, String title) {
        Long start = System.currentTimeMillis();
        final ValidationResult validationResult = contentValidator.validateDocument(fileContent);
        Long stopValidation = System.currentTimeMillis();
        Long startSave = System.currentTimeMillis();
        Long generatedId = -1L;
        if (validationResult == ValidationResult.SUCCESS) {
            generatedId = saveDocumentAndPublishEvent(fileContent, title);
        }
        Long stop = System.currentTimeMillis();
        long result = stop - start;
        long validation = stopValidation - start;
        long save = stop - startSave;
        log.info("FULL:  " + result);
        log.info("VALID: " + validation);
        log.info("SAVE:  " + save);
        //TODO validation is much faster than save, maybe return id that will be assigned to database row???
        return new UploadResult(generatedId, validationResult);
    }

    @Transactional
    Long saveDocumentAndPublishEvent(byte[] fileContent, String title) {
        LocalDateTime now = LocalDateTime.now();
        UploadedDocumentEntity document = new UploadedDocumentEntity();
        document.setTitle(title);
        document.setContent(fileContent);
        document.setTimeAdded(now);
        final UploadedDocumentEntity savedDocument = documentRepository.save(document);
        long generatedId = savedDocument.getId();
        publisher.publishEvent(new NewValidDocumentSavedEvent(generatedId, fileContent));
        return generatedId;
    }

    public Optional<UploadedDocument> getUploadedData(Long id) {
        return documentRepository.findById(id).map(UploadedDocumentEntity::to);
    }

}
