$(function() {
    $.mask.definitions['s']='[0-5]';
    $("#filmTimeFilter").mask("9:s9:s9", {placeholder: "ч:мм:сс" });
    $("#addFilmTime").mask("9:s9:s9", {placeholder: "ч:мм:сс" });
    $("#selectedFilmTime").mask("9:s9:s9", {placeholder: "ч:мм:сс" });
});

function redirecting(path) {
    window.location.href = path;
}

function redirectingOnSelected(path, elementId) {
    var id = document.getElementById(elementId).value;
    window.location.href = path + "?id=" + id + "#openInfo";
}

function stopAnimation(){
    setTimeout(stop, 1000);
}

function stop(){
    var body = document.getElementsByTagName('body')[0];
    body.setAttribute("class", "loaded");
}

function hideElement(elementId, toHide) {
    if (document.getElementById(elementId)) {
        var obj = document.getElementById(elementId);
        if (!toHide) obj.style.display = "block";
        else obj.style.display = "none";
    }
}

function randomCover() {
    var elements = document.getElementsByClassName("grad-cover");
    for (var i = 0; i < elements.length; i++) {
        var firstColor = Math.round((Math.random() * (999999 - 100000) + 100000));
        var secondColor = Math.round((Math.random() * (999999 - 100000) + 100000));
        elements[i].setAttribute("style", "background:  linear-gradient(141deg, #" + firstColor + " 0%, #" + secondColor + " 100%);");
    }
}

function getAddName(addId, addName, page){
    var id = document.getElementById(addId).value;
    jQuery.ajax({
        url: page + "?id=" + id,
        type: "GET",

        success: function (response) {
            var entity = response;
            if (page == "films")
                document.getElementById(addName).value = entity.filmName;
            else if (page == "franchises")
                document.getElementById(addName).value = entity.franchiseName;
            else if (page == "producers")
                document.getElementById(addName).value = entity.producerName;
            else if (page == "genres")
                document.getElementById(addName).value = entity.genreName;
        },

        error: function (response) {
            document.getElementById(addName).value = "";
        }
    });
}

function getSelectedName(redirect, selectedId, selectedName, page){
    var id = document.getElementById(selectedId).value;
    jQuery.ajax({
        url: page + "?id=" + id,
        type: "GET",

        success: function (response) {
            var entity = response;
            if (page == "films")
                document.getElementById(selectedName).value = entity.filmName;
            else if (page == "franchises")
                document.getElementById(selectedName).value = entity.franchiseName;
            else if (page == "producers")
                document.getElementById(selectedName).value = entity.producerName;
            else if (page == "genres")
                document.getElementById(selectedName).value = entity.genreName;
            hideElement(redirect, false); //см
        },

        error: function (response) {
            document.getElementById(selectedName).value = "";
            hideElement(redirect, true);
        }
    });
}

function rectangleClassDateToString(date) {
    var values = dateToString(date).split("-");
    return values[2] + "." + values[1] + "." + values[0];
}
function dateToString(date) {
    var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
    var twoNumberInDay = (date.substring(5, 6) === ",") ? 0 : 1;
    var day = (twoNumberInDay === 0 ? "0" : "") + date.substring(4, 5 + twoNumberInDay);
    var month = date.substring(0, 3);
    var year = date.substring(7 + twoNumberInDay, 11 + twoNumberInDay);
    var digit = 0;
    while (digit < months.length && month != months[digit++]);
    month = digit.toString();
    month = (month.length === 1) ? "0" + month : month;
    return year + "-" + month + "-" + day;
}

function stringToDate(date) {
    if (date === "") return "Jan 1, 0001 00:59:59 AM";

    var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
    var values = date.split("-");
    var day = Number(values[2]);
    var monthIndex = Number(values[1]) - 1;
    var month = months[monthIndex];
    var year = Number(values[0]);
    return month + " " + day + ", " + year + " 12:00:00 AM";
}

function timeToString(time) {
    var twoNumberInDay = (time.substring(5, 6) === ",") ? 0 : 1;
    time = time.substring(12 + twoNumberInDay, 20 + twoNumberInDay);
    var values = time.split(":");
    var hh = (values[0] == 12 ? 0 : values[0]);
    var mm = values[1];
    var ss = values[2];
    return hh + ":" + mm + ":" + ss;
}

//===========================FILM=============================//
function searchFilms() {
    var data = document.getElementById("search_string").value;
    jQuery.ajax({
        url: "films?action=search",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(data),

        success: function(response) {
            var films = response;
            refreshFilms(films);
        },

        error: function(response) {
            alert(response.responseText);
        }
    });
}

function refreshFilms(films) {
    var elems = document.getElementsByClassName("rectangle");
    var count = elems.length - 1;
    for(var i = count; i >= 0; i--){
        elems[i].parentNode.removeChild(elems[i]);
    }
    var mainElem = document.getElementById("main");
    for(var i = films.length - 1; i >= 0; i--){
        var div = document.createElement("div");
        div.setAttribute("class", "rectangle");
        div.setAttribute("id", films[i].filmId);
        var first = mainElem.childNodes[0];
        mainElem.insertBefore(div, first);

        var subDiv = document.createElement("div");
        subDiv.setAttribute("class", "data-element");
        subDiv.setAttribute("onclick", "openSelectedFilm('#openInfo','" + films[i].filmId + "')");
        div.appendChild(subDiv);

        var index = document.createElement("div");
        index.setAttribute("class", "data-index");
        index.innerHTML = films[i].filmId;
        subDiv.appendChild(index);

        var name = document.createElement("div");
        name.setAttribute("class", "data-name");
        name.innerHTML = films[i].filmName;
        subDiv.appendChild(name);

        var time = document.createElement("div");
        time.setAttribute("class", "data-time");
        time.innerHTML = timeToString(films[i].duration);
        subDiv.appendChild(time);

        var subButton = document.createElement("div");
        subButton.setAttribute("class", "data-delete");
        subButton.setAttribute("onclick", "deleteFilm('" + films[i].filmId + "')");
        div.appendChild(subButton);
    }
}

function deleteFilm(id) {
    jQuery.ajax({
        url: "films?action=delete",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(id),

        success: function (response) {
            var elem = document.getElementById(id);
            elem.parentNode.removeChild(elem);
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function clearInfoFilm() {
    document.getElementById("selectedFilmName").value = "";
    document.getElementById("selectedFilmTime").value = "";
    document.getElementById("selectedProducerId").value = "";
    document.getElementById("selectedGenreId").value = "";
    document.getElementById("selectedFranchiseId").value = "";
    document.getElementById("selectedProducerName").value = "";
    document.getElementById("selectedGenreName").value = "";
    document.getElementById("selectedFranchiseName").value = "";
}

function openSelectedFilm(path, filmId) {
    window.location.href = path;
    document.getElementById("selectedFilmId").value = filmId;
    clearInfoFilm();

    jQuery.ajax({
        url: "films?id=" + filmId,
        type: "GET",

        success: function (response) {
            var film = response;
            if (film.producerId !== 0) {
                getAsyncProducer(film.producerId);
                document.getElementById("selectedProducerId").value = film.producerId;
                hideElement("producerRedirect", false);
            }
            else {
                hideElement("producerRedirect", true);
            }
            if (film.genreId !== 0) {
                getAsyncGenre(film.genreId);
                document.getElementById("selectedGenreId").value = film.genreId;
                hideElement("genreRedirect", false);
            }
            else {
                hideElement("genreRedirect", true);
            }
            if (film.franchiseId !== 0) {
                getAsyncFranchise(film.franchiseId);
                document.getElementById("selectedFranchiseId").value = film.franchiseId;
                hideElement("franchiseRedirect", false);
            }
            else {
                hideElement("franchiseRedirect", true);
            }
            document.getElementById("selectedFilmName").value = film.filmName;
            document.getElementById("selectedFilmTime").value = timeToString(film.duration);
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function getAsyncProducer(id) {
    jQuery.ajax({
        url: "producers?id=" + id,
        type: "GET",

        success: function (response) {
            var producer = response;
            document.getElementById("selectedProducerName").value = producer.producerName;
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function getAsyncGenre(id) {
    jQuery.ajax({
        url: "genres?id=" + id,
        type: "GET",

        success: function (response) {
            var genre = response;
            document.getElementById("selectedGenreName").value = genre.genreName;
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function getAsyncFranchise(id) {
    jQuery.ajax({
        url: "franchises?id=" + id,
        type: "GET",

        success: function (response) {
            var franchise = response;
            document.getElementById("selectedFranchiseName").value = franchise.franchiseName;
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function addFilm(){
    var film = {
        filmId: 0,
        filmName: document.getElementById("addFilmName").value,
        duration: "Jan 1, 2018 " + document.getElementById("addFilmTime").value + " AM",
        producerId: document.getElementById("addProducerId").value,
        franchiseId: document.getElementById("addFranchiseId").value,
        genreId: document.getElementById("addGenreId").value
    };

    jQuery.ajax({
        url: "films?action=put",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(film),

        success: function (response) {
            redirecting('#close');
            searchFilms();
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function updateFilm(){
    var film = {
        filmId: document.getElementById("selectedFilmId").value,
        filmName: document.getElementById("selectedFilmName").value,
        duration: "Jan 1, 2018 " + document.getElementById("selectedFilmTime").value + " AM",
        producerId: document.getElementById("selectedProducerId").value,
        genreId: document.getElementById("selectedGenreId").value,
        franchiseId: document.getElementById("selectedFranchiseId").value
    };

    jQuery.ajax({
        url: "films?action=update",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(film),

        success: function (response) {
            redirecting('#close');
            searchFilms();
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function extendedSearchFilms() {
    var time = document.getElementById("filmTimeFilter").value;
    if (time == "") time = "11:00:00";
    var filmFilter = {
        filmName: document.getElementById("filmNameFilter").value,
        duration: "Jan 1, 2018 " + time + " AM",
        producerName: document.getElementById("producerNameFilter").value,
        genreName: document.getElementById("genreNameFilter").value,
        franchiseName: document.getElementById("franchiseNameFilter").value
    };

    jQuery.ajax({
        url: "films?action=extendedsearch",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(filmFilter),

        success: function (response) {
            var films = response;
            refreshFilms(films);
            redirecting('#close');
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function hideEmptyRedirects() {
    var franchiseId = document.getElementById("selectedFranchiseId").value;
    if (franchiseId > 0) hideElement("franchiseRedirect", false);
    else hideElement("franchiseRedirect", true);

    var producerId = document.getElementById("selectedProducerId").value;
    if (producerId > 0) hideElement("producerRedirect", false);
    else hideElement("producerRedirect", true);

    var genreId = document.getElementById("selectedGenreId").value;
    if (genreId > 0) hideElement("genreRedirect", false);
    else hideElement("genreRedirect", true);
}
//===========================PRODUCER=============================//
function searchProducers() {
    var data = document.getElementById("search_string").value;
    jQuery.ajax({
        url: "producers?action=search",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(data),

        success: function(response) {
            var producers = response;
            refreshProducers(producers);
        },

        error: function(response) {
            alert(response.responseText);
        }
    });
}

function refreshProducers(producers) {
    var elems = document.getElementsByClassName("rectangle");
    var count = elems.length - 1;
    for(var i = count; i >= 0; i--){
        elems[i].parentNode.removeChild(elems[i]);
    }
    var mainElem = document.getElementById("main");
    for(var i = producers.length - 1; i >= 0; i--){
        var div = document.createElement("div");
        div.setAttribute("id", producers[i].producerId);
        div.setAttribute("class", "rectangle");
        var first = mainElem.childNodes[0];
        mainElem.insertBefore(div, first);

        var subDiv = document.createElement("div");
        subDiv.setAttribute("class", "data-element");
        subDiv.setAttribute("onclick", "openSelectedProducer('#openInfo','" + producers[i].producerId + "')");
        div.appendChild(subDiv);

        var index = document.createElement("div");
        index.setAttribute("class", "data-index");
        index.innerHTML = producers[i].producerId;
        subDiv.appendChild(index);

        var name = document.createElement("div");
        name.setAttribute("class", "data-name");
        name.setAttribute("style", "width: 75%;");
        name.innerHTML = producers[i].producerName;
        subDiv.appendChild(name);

        var release = document.createElement("div");
        release.setAttribute("class", "data-time");
        release.setAttribute("style", "width: 15%;");
        release.innerHTML = rectangleClassDateToString(producers[i].birthdate);
        subDiv.appendChild(release);

        var subButton = document.createElement("div");
        subButton.setAttribute("class", "data-delete");
        subButton.setAttribute("onclick", "deleteProducer('" + producers[i].producerId + "')");
        div.appendChild(subButton);
    }
}

function extendedSearchProducers() {
    var producerFilter = {
        producerName: document.getElementById("producerNameFilter").value,
        birthdate: stringToDate(document.getElementById("producerBirthdateFilter").value),
        citizenship: document.getElementById("producerCitizenshipFilter").value
    };

    jQuery.ajax({
        url: "producers?action=extendedsearch",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(producerFilter),

        success: function (response) {
            var producers = response;
            refreshProducers(producers);
            redirecting('#close');
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function addProducer(){
    var producer = {
        producerId: 0,
        producerName: document.getElementById("addProducerName").value,
        birthdate: stringToDate(document.getElementById("addProducerBirthdate").value),
        citizenship: document.getElementById("addProducerCitizenship").value
    };

    jQuery.ajax({
        url: "producers?action=put",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(producer),

        success: function (response) {
            redirecting('#close');
            searchProducers();
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function deleteProducer(id) {
    jQuery.ajax({
        url: "producers?action=delete",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(id),

        success: function (response) {
            var elem = document.getElementById(id);
            elem.parentNode.removeChild(elem);
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function clearInfoProducer() {
    document.getElementById("selectedProducerName").value = "";
    document.getElementById("selectedProducerBirthdate").value = "";
    document.getElementById("selectedProducerCitizenship").value = "";
}

function openSelectedProducer(path, producerId) {
    window.location.href = path;
    document.getElementById("selectedProducerId").value = producerId;
    clearInfoProducer();

    jQuery.ajax({
        url: "producers?id=" + producerId,
        type: "GET",

        success: function (response) {
            var producer = response;
            document.getElementById("selectedProducerName").value = producer.producerName;
            document.getElementById("selectedProducerBirthdate").value = dateToString(producer.birthdate);
            document.getElementById("selectedProducerCitizenship").value = producer.citizenship;
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function updateProducer(){
    var producer = {
        producerId: document.getElementById("selectedProducerId").value,
        producerName: document.getElementById("selectedProducerName").value,
        birthdate: stringToDate(document.getElementById("selectedProducerBirthdate").value),
        citizenship: document.getElementById("selectedProducerCitizenship").value
    };

    jQuery.ajax({
        url: "producers?action=update",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(producer),

        success: function (response) {
            redirecting('#close');
            searchProducers();
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

//===========================FRANCHISE=============================//
function searchFranchises() {
    var data = document.getElementById("search_string").value;
    jQuery.ajax({
        url: "franchises?action=search",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(data),

        success: function(response) {
            var franchises = response;
            refreshFranchises(franchises);
        },

        error: function(response) {
            alert(response.responseText);
        }
    });
}

function refreshFranchises(franchise) {
    var elems = document.getElementsByClassName("cover");
    var count = elems.length - 1;
    for(var i = count; i >= 0; i--){
        elems[i].parentNode.removeChild(elems[i]);
    }
    var mainElem = document.getElementById("main");
    for(var i = franchise.length - 1; i >= 0; i--){
        var div = document.createElement("div");
        div.setAttribute("class", "cover");
        div.setAttribute("id", franchise[i].franchiseId);
        var first = mainElem.childNodes[0];
        mainElem.insertBefore(div, first);

        var subDiv = document.createElement("div");
        subDiv.setAttribute("class", "grad-cover");
        div.appendChild(subDiv);

        var deleteButton = document.createElement("div");
        deleteButton.setAttribute("class", "text-delete");
        deleteButton.setAttribute("onclick", "deleteFranchise('" + franchise[i].franchiseId + "')");
        deleteButton.innerHTML = "X";
        div.appendChild(deleteButton);

        var shadow = document.createElement("div");
        shadow.setAttribute("class", "shadow");
        shadow.setAttribute("onclick", "openSelectedFranchise('#openInfo','" + franchise[i].franchiseId + "')");
        div.appendChild(shadow);

        var name = document.createElement("div");
        name.setAttribute("class", "text-name");
        name.innerHTML = franchise[i].franchiseName;
        shadow.appendChild(name);

        var index = document.createElement("div");
        index.setAttribute("class", "text-id");
        index.innerHTML = franchise[i].franchiseId;
        shadow.appendChild(index);
    }
    randomCover();
}

function deleteFranchise(id) {
    jQuery.ajax({
        url: "franchises?action=delete",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(id),

        success: function (response) {
            var elem = document.getElementById(id);
            elem.parentNode.removeChild(elem);
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function clearInfoFranchise() {
    document.getElementById("selectedFranchiseName").value = "";
    document.getElementById("selectedFranchiseRelease").value = "";
    document.getElementById("selectedFranchiseCountry").value = "";
}

function openSelectedFranchise(path, franchiseId) {
    window.location.href = path;
    document.getElementById("selectedFranchiseId").value = franchiseId;
    clearInfoFranchise();

    jQuery.ajax({
        url: "franchises?id=" + franchiseId,
        type: "GET",

        success: function (response) {
            var franchise = response;
            document.getElementById("selectedFranchiseName").value = franchise.franchiseName;
            document.getElementById("selectedFranchiseRelease").value = dateToString(franchise.release);
            document.getElementById("selectedFranchiseCountry").value = franchise.country;
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function addFranchise(){
    var franchise = {
        franchiseId: 0,
        franchiseName: document.getElementById("addFranchiseName").value,
        release: stringToDate(document.getElementById("addFranchiseRelease").value),
        country: document.getElementById("addFranchiseCountry").value
    };

    jQuery.ajax({
        url: "franchises?action=put",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(franchise),

        success: function (response) {
            redirecting('#close');
            searchFranchises();
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function updateFranchise(){
    var franchise = {
        franchiseId: document.getElementById("selectedFranchiseId").value,
        franchiseName: document.getElementById("selectedFranchiseName").value,
        release: stringToDate(document.getElementById("selectedFranchiseRelease").value),
        country: document.getElementById("selectedFranchiseCountry").value
    };

    jQuery.ajax({
        url: "franchises?action=update",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(franchise),

        success: function (response) {
            redirecting('#close');
            searchFranchises();
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function extendedSearchFranchises() {

    var franchiseFilter = {
        franchiseName: document.getElementById("franchiseNameFilter").value,
        release: stringToDate(document.getElementById("franchiseReleaseFilter").value),
        country: document.getElementById("franchiseCountryFilter").value
    };

    jQuery.ajax({
        url: "franchises?action=extendedsearch",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(franchiseFilter),

        success: function (response) {
            var franchises = response;
            refreshFranchises(franchises);
            redirecting('#close');
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

//===========================GENRE=============================//
function searchGenres() {
    var data = document.getElementById("search_string").value;
    jQuery.ajax({
        url: "genres?action=search",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(data),

        success: function(response) {
            var genres = response;
            refreshGenres(genres);
        },

        error: function(response) {
            alert(response.responseText);
        }
    });
}

function refreshGenres(genres) {
    var elems = document.getElementsByClassName("cover");
    var count = elems.length - 1;
    for(var i = count; i >= 0; i--){
        elems[i].parentNode.removeChild(elems[i]);
    }
    var mainElem = document.getElementById("main");
    for(var i = genres.length - 1; i >= 0; i--){
        var div = document.createElement("div");
        div.setAttribute("class", "cover");
        div.setAttribute("id", genres[i].genreId);
        var first = mainElem.childNodes[0];
        mainElem.insertBefore(div, first);

        var subDiv = document.createElement("div");
        subDiv.setAttribute("class", "grad-cover");
        div.appendChild(subDiv);

        var deleteButton = document.createElement("div");
        deleteButton.setAttribute("class", "text-delete");
        deleteButton.setAttribute("onclick", "deleteGenre('" + genres[i].genreId + "')");
        deleteButton.innerHTML = "X";
        div.appendChild(deleteButton);

        var shadow = document.createElement("div");
        shadow.setAttribute("class", "shadow");
        shadow.setAttribute("onclick", "openSelectedGenre('#openInfo','" + genres[i].genreId + "')");
        div.appendChild(shadow);

        var name = document.createElement("div");
        name.setAttribute("class", "text-name");
        name.innerHTML = genres[i].genreName;
        shadow.appendChild(name);

        var index = document.createElement("div");
        index.setAttribute("class", "text-id");
        index.innerHTML = genres[i].genreId;
        shadow.appendChild(index);
    }
    randomCover();
}

function deleteGenre(id) {
    jQuery.ajax({
        url: "genres?action=delete",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(id),

        success: function (response) {
            var elem = document.getElementById(id);
            elem.parentNode.removeChild(elem);
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function clearInfoGenre() {
    document.getElementById("selectedGenreName").value = "";
    document.getElementById("selectedParentName").value = "";
    document.getElementById("selectedParentId").value = "";
}

function openSelectedGenre(path, genreId) {
    window.location.href = path;
    document.getElementById("selectedGenreId").value = genreId;
    clearInfoGenre();

    jQuery.ajax({
        url: "genres?id=" + genreId,
        type: "GET",

        success: function (response) {
            var genre = response;
            if (genre.parentId !== 0) {
                getAsyncParentGenre(genre.parentId);
                document.getElementById("selectedParentId").value = genre.parentId;
                hideElement("parentRedirect", false);
            }
            else {
                hideElement("parentRedirect", true);
            }
            document.getElementById("selectedGenreName").value = genre.genreName;
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function getAsyncParentGenre(id) {
    jQuery.ajax({
        url: "genres?id=" + id,
        type: "GET",

        success: function (response) {
            var genre = response;
            document.getElementById("selectedParentName").value = genre.genreName;
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function addGenre(){
    var parentId = document.getElementById("addParentId").value;
    var genre = {
        genreId: 0,
        genreName: document.getElementById("addGenreName").value,
        parentId: (parentId == "" ? 0 : parentId)
    };

    jQuery.ajax({
        url: "genres?action=put",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(genre),

        success: function (response) {
            redirecting('#close');
            searchGenres();
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function updateGenre(){
    var parentId = document.getElementById("selectedParentId").value;
    var genre = {
        genreId: document.getElementById("selectedGenreId").value,
        genreName: document.getElementById("selectedGenreName").value,
        parentId: (parentId == "" ? 0 : parentId)
    };

    jQuery.ajax({
        url: "genres?action=update",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(genre),

        success: function (response) {
            redirecting('#close');
            searchGenres();
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function extendedSearchGenres() {
    var genreFilter = {
        genreName: document.getElementById("genreNameFilter").value,
        parentName: document.getElementById("parentNameFilter").value
    };

    jQuery.ajax({
        url: "genres?action=extendedsearch",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(genreFilter),

        success: function (response) {
            var genres = response;
            refreshGenres(genres);
            redirecting('#close');
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function hideEmptyParentGenreAndShowCovers() {
    randomCover();
    var parentId = document.getElementById("selectedParentId").value;
    if (parentId > 0) hideElement("parentRedirect", false);
    else hideElement("parentRedirect", true);
}