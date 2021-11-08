const userSidebar = document.getElementById("sidebar-nav-users");
const sidebar = document.getElementById("sidebar-nav");
const loadMoreButton = document.getElementById("load-more-button")

function addDummyEntryToUserList(uuid, title) {
    let newEntry = createNewDocumentEntry(uuid, title);
    newEntry.classList.add("text-light")
    newEntry.setAttribute("id", uuid);
    newEntry.disabled = true
    addLoaderToEntry(newEntry)
    userSidebar.insertBefore(newEntry, userSidebar.firstChild)
}

function addVerifiedEntryToUserList(uuid, title, validationPassed) {
    let newEntry = createNewDocumentEntry(uuid, title);
    newEntry.setAttribute("id", uuid);
    if (validationPassed) {
        newEntry.classList.add("text-light")
    } else {
        newEntry.classList.add("text-danger")
        newEntry.disabled = true
    }
    userSidebar.appendChild(newEntry)
}

function addEntryToAllDocumentsList(uuid, title, addOnTop) {
    let newEntry = createNewDocumentEntry(uuid, title);
    newEntry.classList.add("text-light")
    if (addOnTop) {
        sidebar.insertBefore(newEntry, sidebar.firstChild)
    } else {
        sidebar.insertBefore(newEntry, loadMoreButton)
    }
}

function createNewDocumentEntry(uuid, title) {
    let newEntry = document.createElement("a");
    newEntry.setAttribute("title", uuid);
    newEntry.setAttribute("onClick", "getGraph(this)");
    newEntry.setAttribute("href", "#");
    newEntry.setAttribute("class", "list-group-item list-group-item-action bg-dark cut-text");
    newEntry.appendChild(document.createTextNode(title))
    return newEntry
}