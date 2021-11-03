package com.swiatek.benford.document;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class UploadedDocument {
    Long id;

    UUID uuid;

    String title;

    LocalDateTime timeAdded;
}
