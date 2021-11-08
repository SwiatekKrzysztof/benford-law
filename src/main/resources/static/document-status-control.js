function changeDocumentEntryValidationStatus(uuid, validationPassed) {
    let foundDocument = document.getElementById(uuid);
    removeLoaderFromEntry(foundDocument)

    if (validationPassed) {
        foundDocument.classList.replace("text-light", "text-info")
        // foundDocument.classList.add("text-info")
        // foundDocument.innerHTML = "<span class='text-info'>" + foundDocument.innerHTML + "</span>"
    } else {
        foundDocument.classList.replace("text-light", "text-danger")
        // foundDocument.classList.add("text-danger")
        // foundDocument.innerHTML = "<span class='text-danger'>" + foundDocument.innerHTML + "</span>"
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

