function updateLocalStorage(uuid) {
    if (uuid !== null) {
        let storage = window.localStorage;
        let uuids = JSON.parse(storage.getItem("UUIDs"));
        if (uuids !== null) {
            uuids.push(uuid)
            storage.setItem("UUIDs", JSON.stringify(uuids))
        } else {
            let newListOfUUIDs = [uuid]
            storage.setItem("UUIDs", JSON.stringify(newListOfUUIDs))
        }
    }
}

function getUserUUIDs() {
    let storage = window.localStorage;
    return JSON.parse(storage.getItem("UUIDs"))
}