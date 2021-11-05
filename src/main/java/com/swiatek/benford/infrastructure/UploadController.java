package com.swiatek.benford.infrastructure;

import com.swiatek.benford.document.DocumentFacade;
import com.swiatek.benford.document.result.UploadResult;
import com.swiatek.benford.document.result.ValidationResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/upload")
@Slf4j
@AllArgsConstructor
public class UploadController {
    DocumentFacade documentFacade;

    @ResponseBody
    @PostMapping(value = "/file")
    public Mono<ResponseEntity<UUID>> uploadFileAndAssignUuid(@RequestPart Mono<FilePart> file) {
        return file.flatMap(filePart -> documentFacade.uploadDocumentAndAssignUuid(file, filePart.filename()))
                .map(ResponseEntity::ok);
    }
}
