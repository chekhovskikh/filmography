$(function() {
    $.mask.definitions['h']='[0-5]';
    $("#songTimeFilter").mask("h9:h9", {placeholder: "мм:сс" });
    $("#addSongTime").mask("h9:h9", {placeholder: "мм:сс" });
    $("#selectedSongTime").mask("h9:h9", {placeholder: "мм:сс" });
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
    var values = date.value.split("-");
    var day = Number(values[2]);
    var monthIndex = Number(values[1]) - 1;
    var month = months[monthIndex];
    var year = Number(values[0]);
    return month + " " + day + ", " + year + " 12:00:00 AM";
}

function timeToString(time) {
    var twoNumberInDay = (time.substring(5, 6) === ",") ? 0 : 1;
    return time.substring(15 + twoNumberInDay, 20 + twoNumberInDay);
}

//===========================SONG=============================//
function searchSongs() {
    var data = document.getElementById("search_string").value;
    jQuery.ajax({
        url: "songs?action=search",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(data),

        success: function(response) {
            var songs = response;
            refreshSongs(songs);
        },

        error: function(response) {
            alert(response.responseText);
        }
    });
}

function refreshSongs(songs) {
    var elems = document.getElementsByClassName("rectangle");
    var count = elems.length - 1;
    for(var i = count; i >= 0; i--){
        elems[i].parentNode.removeChild(elems[i]);
    }
    var mainElem = document.getElementById("main");
    for(var i = songs.length - 1; i >= 0; i--){
        var div = document.createElement("div");
        div.setAttribute("class", "rectangle");
        div.setAttribute("id", songs[i].id);
        var first = mainElem.childNodes[0];
        mainElem.insertBefore(div, first);

        var subDiv = document.createElement("div");
        subDiv.setAttribute("class", "data-element");
        subDiv.setAttribute("onclick", "openSelectedSong('#openInfo','" + songs[i].id + "')");
        div.appendChild(subDiv);

        var index = document.createElement("div");
        index.setAttribute("class", "data-index");
        index.innerHTML = songs[i].id;
        subDiv.appendChild(index);

        var name = document.createElement("div");
        name.setAttribute("class", "data-name");
        name.innerHTML = songs[i].name;
        subDiv.appendChild(name);

        var time = document.createElement("div");
        time.setAttribute("class", "data-time");
        time.innerHTML = timeToString(songs[i].time);
        subDiv.appendChild(time);

        var subButton = document.createElement("div");
        subButton.setAttribute("class", "data-delete");
        subButton.setAttribute("onclick", "deleteSong('" + songs[i].id + "')");
        div.appendChild(subButton);
    }
}

function deleteSong(id) {
    jQuery.ajax({
        url: "songs?action=delete",
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

function clearInfoSong() {
    document.getElementById("selectedSongName").value = "";
    document.getElementById("selectedSongTime").value = "";
    document.getElementById("selectedBandId").value = "";
    document.getElementById("selectedGenreId").value = "";
    document.getElementById("selectedAlbumId").value = "";
    document.getElementById("selectedBandName").value = "";
    document.getElementById("selectedGenreName").value = "";
    document.getElementById("selectedAlbumName").value = "";
}

function openSelectedSong(path, songId) {
    window.location.href = path;
    document.getElementById("selectedSongId").value = songId;
    clearInfoSong();

    jQuery.ajax({
        url: "songs?id=" + songId,
        type: "GET",

        success: function (response) {
            var song = response;
            if (song.bandId !== 0) {
                getAsyncBand(song.bandId);
                document.getElementById("selectedBandId").value = song.bandId;
                hideElement("bandRedirect", false);
            }
            else {
                hideElement("bandRedirect", true);
            }
            if (song.genreId !== 0) {
                getAsyncGenre(song.genreId);
                document.getElementById("selectedGenreId").value = song.genreId;
                hideElement("genreRedirect", false);
            }
            else {
                hideElement("genreRedirect", false);
            }
            if (song.albumId !== 0) {
                getAsyncAlbum(song.albumId);
                document.getElementById("selectedAlbumId").value = song.albumId;
                hideElement("albumRedirect", false);
            }
            else {
                hideElement("albumRedirect", false);
            }
            document.getElementById("selectedSongName").value = song.name;
            document.getElementById("selectedSongTime").value = timeToString(song.time);
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function getAsyncBand(id) {
    jQuery.ajax({
        url: "bands?id=" + id,
        type: "GET",

        success: function (response) {
            var band = response;
            document.getElementById("selectedBandName").value = band.name;
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
            document.getElementById("selectedGenreName").value = genre.name;
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function getAsyncAlbum(id) {
    jQuery.ajax({
        url: "albums?id=" + id,
        type: "GET",

        success: function (response) {
            var album = response;
            document.getElementById("selectedAlbumName").value = album.name;
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function addSong(){
    var song = {
        id: 0,
        name: document.getElementById("addSongName").value,
        time: "Jan 1, 2018 12:" + document.getElementById("addSongTime").value + " AM",
        bandId: document.getElementById("addBandId").value,
        albumId: document.getElementById("addGenreId").value,
        genreId: document.getElementById("addAlbumId").value
    };

    jQuery.ajax({
        url: "songs?action=put",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(song),

        success: function (response) {
            redirecting('#close');
            searchSongs();
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function updateSong(){
    var song = {
        id: document.getElementById("selectedSongId").value,
        name: document.getElementById("selectedSongName").value,
        time: "Jan 1, 2018 12:" + document.getElementById("selectedSongTime").value + " AM",
        bandId: document.getElementById("selectedBandId").value,
        albumId: document.getElementById("selectedGenreId").value,
        genreId: document.getElementById("selectedAlbumId").value
    };

    jQuery.ajax({
        url: "songs?action=update",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(song),

        success: function (response) {
            redirecting('#close');
            searchSongs();
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function extendedSearchSongs() {
    var time = document.getElementById("songTimeFilter").value;
    if (time == "") time = "00:00";
    var songFilter = {
        name: document.getElementById("songNameFilter").value,
        time: "Jan 1, 2018 12:" + time + " AM",
        bandName: document.getElementById("bandNameFilter").value,
        albumName: document.getElementById("genreNameFilter").value,
        genreName: document.getElementById("albumNameFilter").value
    };

    jQuery.ajax({
        url: "songs?action=extendedsearch",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(songFilter),

        success: function (response) {
            var songs = response;
            refreshSongs(songs);
            redirecting('#close');
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

//===========================BAND=============================//
function searchBands() {
    var data = document.getElementById("search_string").value;
    jQuery.ajax({
        url: "bands?action=search",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(data),

        success: function(response) {
            var bands = response;
            refreshBands(bands);
        },

        error: function(response) {
            alert(response.responseText);
        }
    });
}

function refreshBands(bands) {
    var elems = document.getElementsByClassName("rectangle");
    var count = elems.length - 1;
    for(var i = count; i >= 0; i--){
        elems[i].parentNode.removeChild(elems[i]);
    }
    var mainElem = document.getElementById("main");
    for(var i = bands.length - 1; i >= 0; i--){
        var div = document.createElement("div");
        div.setAttribute("class", "rectangle");
        div.setAttribute("id", bands[i].id);
        var first = mainElem.childNodes[0];
        mainElem.insertBefore(div, first);

        var subDiv = document.createElement("div");
        subDiv.setAttribute("class", "data-element");
        subDiv.setAttribute("onclick", "openSelectedBand('#openInfo','" + bands[i].id + "')");
        div.appendChild(subDiv);

        var index = document.createElement("div");
        index.setAttribute("class", "data-index");
        index.innerHTML = bands[i].id;
        subDiv.appendChild(index);

        var name = document.createElement("div");
        name.setAttribute("class", "data-name");
        name.setAttribute("style", "width: 75%;");
        name.innerHTML = bands[i].name;
        subDiv.appendChild(name);

        var release = document.createElement("div");
        release.setAttribute("class", "data-time");
        release.setAttribute("style", "width: 15%;");
        release.innerHTML = rectangleClassDateToString(bands[i].foundation);
        subDiv.appendChild(release);

        var subButton = document.createElement("div");
        subButton.setAttribute("class", "data-delete");
        subButton.setAttribute("onclick", "deleteBand('" + bands[i].id + "')");
        div.appendChild(subButton);
    }
}

function extendedSearchBands() {
    var bandFilter = {
        name: document.getElementById("bandNameFilter").value,
        foundation: stringToDate(document.getElementById("bandFoundationFilter").value)
    };

    jQuery.ajax({
        url: "bands?action=extendedsearch",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(bandFilter),

        success: function (response) {
            var bands = response;
            refreshBands(bands);
            redirecting('#close');
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function addBand(){
    var band = {
        id: 0,
        name: document.getElementById("addBandName").value,
        foundation: stringToDate(document.getElementById("addBandFoundation").value)
    };

    jQuery.ajax({
        url: "bands?action=put",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(band),

        success: function (response) {
            redirecting('#close');
            searchBands();
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function deleteBand(id) {
    jQuery.ajax({
        url: "bands?action=delete",
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

function clearInfoBand() {
    document.getElementById("selectedBandName").value = "";
    document.getElementById("selectedBandFoundation").value = "";
}

function openSelectedBand(path, bandId) {
    window.location.href = path;
    document.getElementById("selectedBandId").value = bandId;
    clearInfoBand();

    jQuery.ajax({
        url: "bands?id=" + bandId,
        type: "GET",

        success: function (response) {
            var band = response;
            document.getElementById("selectedBandName").value = band.name;
            document.getElementById("selectedBandFoundation").value = dateToString(band.foundation);
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function updateBand(){
    var band = {
        id: document.getElementById("selectedBandId").value,
        name: document.getElementById("selectedBandName").value,
        foundation: stringToDate(document.getElementById("selectedBandFoundation").value)
    };

    jQuery.ajax({
        url: "bands?action=update",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(band),

        success: function (response) {
            redirecting('#close');
            searchBands();
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

//===========================ALBUM=============================//
function searchAlbums() {
    var data = document.getElementById("search_string").value;
    jQuery.ajax({
        url: "albums?action=search",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(data),

        success: function(response) {
            var albums = response;
            refreshAlbums(albums);
        },

        error: function(response) {
            alert(response.responseText);
        }
    });
}

function refreshAlbums(albums) {
    var elems = document.getElementsByClassName("cover");
    var count = elems.length - 1;
    for(var i = count; i >= 0; i--){
        elems[i].parentNode.removeChild(elems[i]);
    }
    var mainElem = document.getElementById("main");
    for(var i = albums.length - 1; i >= 0; i--){
        var div = document.createElement("div");
        div.setAttribute("class", "cover");
        div.setAttribute("id", albums[i].id);
        var first = mainElem.childNodes[0];
        mainElem.insertBefore(div, first);

        var subDiv = document.createElement("div");
        subDiv.setAttribute("class", "grad-cover");
        div.appendChild(subDiv);

        var deleteButton = document.createElement("div");
        deleteButton.setAttribute("class", "text-delete");
        deleteButton.setAttribute("onclick", "deleteAlbum('" + albums[i].id + "')");
        deleteButton.innerHTML = "X";
        div.appendChild(deleteButton);

        var shadow = document.createElement("div");
        shadow.setAttribute("class", "shadow");
        shadow.setAttribute("onclick", "openSelectedAlbum('#openInfo','" + albums[i].id + "')");
        div.appendChild(shadow);

        var name = document.createElement("div");
        name.setAttribute("class", "text-name");
        name.innerHTML = albums[i].name;
        shadow.appendChild(name);

        var index = document.createElement("div");
        index.setAttribute("class", "text-id");
        index.innerHTML = albums[i].id;
        shadow.appendChild(index);
    }
    randomCover();
}

function deleteAlbum(id) {
    jQuery.ajax({
        url: "albums?action=delete",
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

function clearInfoAlbum() {
    document.getElementById("selectedAlbumName").value = "";
    document.getElementById("selectedAlbumRelease").value = "";
    document.getElementById("selectedBandId").value = "";
    document.getElementById("selectedBandName").value = "";
}

function openSelectedAlbum(path, albumId) {
    window.location.href = path;
    document.getElementById("selectedAlbumId").value = albumId;
    clearInfoAlbum();

    jQuery.ajax({
        url: "albums?id=" + albumId,
        type: "GET",

        success: function (response) {
            var album = response;
            if (album.bandId !== 0) {
                getAsyncBand(album.bandId);
                document.getElementById("selectedBandId").value = album.bandId;
                hideElement("bandRedirect", false);
            }
            else {
                hideElement("bandRedirect", true);
            }
            document.getElementById("selectedAlbumName").value = album.name;
            document.getElementById("selectedAlbumRelease").value = dateToString(album.release);
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function addAlbum(){
    var album = {
        id: 0,
        name: document.getElementById("addAlbumName").value,
        release: stringToDate(document.getElementById("addAlbumRelease")),
        bandId: document.getElementById("addBandId").value
    };

    jQuery.ajax({
        url: "albums?action=put",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(album),

        success: function (response) {
            redirecting('#close');
            searchAlbums();
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function updateAlbum(){
    var album = {
        id: document.getElementById("selectedAlbumId").value,
        name: document.getElementById("selectedAlbumName").value,
        release: stringToDate(document.getElementById("selectedAlbumRelease")),
        bandId: document.getElementById("selectedBandId").value
    };

    jQuery.ajax({
        url: "albums?action=update",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(album),

        success: function (response) {
            redirecting('#close');
            searchAlbums();
        },

        error: function (response) {
            alert(response.responseText);
        }
    });
}

function extendedSearchAlbums() {

    var albumFilter = {
        name: document.getElementById("albumNameFilter").value,
        foundation: stringToDate(document.getElementById("albumReleaseFilter").value),
        bandName: document.getElementById("bandNameFilter").value
    };

    jQuery.ajax({
        url: "albums?action=extendedsearch",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(albumFilter),

        success: function (response) {
            var albums = response;
            refreshAlbums(albums);
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