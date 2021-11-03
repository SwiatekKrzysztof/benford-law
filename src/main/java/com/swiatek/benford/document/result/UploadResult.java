package com.swiatek.benford.document.result;

import java.util.UUID;

public record UploadResult(UUID uuid, ValidationResult validationResult) {
}
