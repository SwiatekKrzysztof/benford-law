package com.swiatek.benford.infrastructure;

import com.swiatek.benford.document.DocumentFacade;
import com.swiatek.benford.document.result.UploadResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
@Slf4j
@AllArgsConstructor
public class UploadController {
    DocumentFacade documentFacade;

    @PostMapping("/file")
    public ResponseEntity<UploadResult> uploadFile(@RequestBody MultipartFile file) throws IOException {
        final UploadResult uploadResult = documentFacade.uploadDocument(file.getInputStream().readAllBytes(), file.getName());
        return ResponseEntity.ok(uploadResult);
    }
}
