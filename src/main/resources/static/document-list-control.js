function addNewDocument


function addNewDocumentToChosenDocumentList(uuid, title, addToUserSidebar, fieldDisabled, validationPassed, initialAdd) {
    let newEntry = createNewDocumentEntry(uuid, title, fieldDisabled, validationPassed)
    if (addToUserSidebar) {
        addEntryToSidebar(newEntry, userSidebar, initialAdd)
    } else {
        addEntryToSidebar(newEntry, sidebar, initialAdd);
        allDocuments.push({"uuid": uuid, "validationPassed": validationPassed})
    }
}

function addEntryToSidebar(newEntry, sidebar, initialAdd) {
    if(initialAdd) {
        sidebar.appendChild(newEntry)
    } else {
        sidebar.insertBefore(newEntry, sidebar.firstChild)
    }
}

function createNewDocumentEntry(uuid, title, entryDisabled, validationPassed) {
    let newEntry = document.createElement("a");
    newEntry.setAttribute("title", uuid);
    newEntry.setAttribute("id", uuid);
    newEntry.setAttribute("onClick", "getGraph(this)");
    newEntry.setAttribute("href", "#");
    newEntry.setAttribute("class", "list-group-item list-group-item-action bg-dark");
    newEntry.appendChild(document.createTextNode(title))

    if(validationPassed) {
        newEntry.classList.add("text-light")
    } else {
        newEntry.classList.add("text-danger")
    }
    if (entryDisabled) {
        newEntry.disabled = true
        addLoaderToEntry(newEntry)
    }
    return newEntry
}