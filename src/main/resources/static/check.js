function checkInput(flg) {

    let longitude = document.getElementById('longitude');
    let latitude = document.getElementById('latitude');
    if (flg == 0) {
        longitude = document.getElementById('longitude1');
        latitude = document.getElementById('latitude1');
    }

    if (longitude.value.trim() == "") {
        alert("please input the longitude!");
        return false;
    } else if (latitude.value.trim() == "") {
        alert("please input the latitude!");
        return false;
    }

    if (flg == 0) {
        let customers = document.getElementById('customers');
        if (customers.value.trim() == "") {
            alert("please input the customers!");
            return false;
        }
    }
    else {
        let file = document.getElementById('file');
        if (file.value.trim() == "") {
            alert("please choose the file!");
            return false;
        }
    }
    return true;
}
