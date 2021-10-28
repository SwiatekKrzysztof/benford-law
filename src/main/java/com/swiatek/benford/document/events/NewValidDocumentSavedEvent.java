package com.swiatek.benford.document.events;

import lombok.Value;

@Value
public class NewValidDocumentSavedEvent {
    long documentId;
    byte[] fileContent;
}
