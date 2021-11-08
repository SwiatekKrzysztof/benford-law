package com.swiatek.benford.infrastructure;

import com.swiatek.benford.document.DocumentFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/file")
@Slf4j
@AllArgsConstructor
public class FileTransferController {
    DocumentFacade documentFacade;

    @ResponseBody
    @PostMapping(value = "/upload")
    public Mono<ResponseEntity<UUID>> uploadFileAndAssignUuid(@RequestPart Mono<FilePart> file) {
        return file.flatMap(filePart -> documentFacade.uploadDocumentAndAssignUuid(file, filePart.filename()))
                .map(ResponseEntity::ok);
    }


    @GetMapping("/download/{uuid}")
    @ResponseBody
    Mono<ResponseEntity<Resource>> getDocumentFileContent(@PathVariable String uuid) {
        return documentFacade.getFileByUuid(UUID.fromString(uuid))
                .map(ResponseEntity::ok);
    }
}
