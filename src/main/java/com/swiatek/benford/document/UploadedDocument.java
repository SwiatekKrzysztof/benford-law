package com.swiatek.benford.document;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class UploadedDocument {
    Long id;

    String title;

    LocalDateTime timeAdded;

    byte[] content;
}
