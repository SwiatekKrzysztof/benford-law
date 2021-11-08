// const DOCUMENT_UPLOADED_EVENT_NAME = "documentUploaded"
// const DOCUMENT_VERIFIED_EVENT_NAME = "documentVerified"
// const GRAPH_CREATED_EVENT_NAME = "graphCreated"

const EVENT_NAME = {
    DOCUMENT_UPLOADED: "documentUploaded",
    DOCUMENT_VERIFIED: "documentVerified",
    GRAPH_CREATED: "graphCreated"
}

class DocumentUploaded extends Event {
    constructor(uuid, title) {
        super(EVENT_NAME.DOCUMENT_UPLOADED);
        this.uuid = uuid;
        this.title = title;
    }
}

class DocumentVerified extends Event {
    constructor(uuid, title, verificationResult) {
        super(EVENT_NAME.DOCUMENT_VERIFIED);
        this.uuid = uuid;
        this.title = title;
        this.verificationResult = verificationResult;
    }
}

class GraphCreated extends Event {
    constructor(uuid) {
        super(EVENT_NAME.GRAPH_CREATED);
        this.uuid = uuid;
    }
}

function documentUploaded(uuid, title) {
    window.dispatchEvent(new DocumentUploaded(uuid, title))
    console.log("EVENT PUBLISHED: DOCUMENT UPLOADED")
}

function documentVerified(uuid, title, verificationResult) {
    window.dispatchEvent(new DocumentVerified(uuid, title, verificationResult))
    console.log("EVENT PUBLISHED: DOCUMENT VERIFIED")
    console.log(DocumentVerified.name)
}

function graphCreated(uuid) {
    window.dispatchEvent(new GraphCreated(uuid))
    console.log("EVENT PUBLISHED: GRAPH CREATED")
}
