function changeDocumentEntryValidationStatus(uuid, validationPassed) {
    let foundDocument = document.getElementById(uuid);
    removeLoaderFromEntry(foundDocument)
    if (validationPassed) {
        foundDocument.classList.replace("text-light", "text-info")
        foundDocument.disabled = false
    } else {
        foundDocument.classList.replace("text-light", "text-danger")
        foundDocument.disabled = true
    }
}

function addLoaderToEntry(entry) {
    let loaderDiv = document.createElement("div");
    loaderDiv.className = "lds-dual-ring"
    entry.appendChild(loaderDiv)
}

function removeLoaderFromEntry(entry) {
    let children = entry.childNodes
    for (let i = 0; i < children.length; i++) {
        if (children[i].className !== undefined && children[i].className.includes("lds-dual-ring")) {
            entry.removeChild(children[i])
        }
    }
}

