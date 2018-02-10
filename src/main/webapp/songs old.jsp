<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags/page" prefix="page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/searchbar.css"/>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts.js"></script>

<html>
<head>
    <title>Songs</title>
    <page:header title="Меню"/>
</head>
<hr/>
<form action="songs" method="POST">
    <input type="hidden" name="action" value="put">
    Название песни:<br/>
    <input type="text" name="name" value="" title="Название" required>
    <br/><br/>
    Длительность:<br/>
    <input type="text" name="time" value="" title="Длительность" required>
    <br/><br/>
    ID Группы: <br/>
        <input style="float: left;"  onchange="getNameBand(true)" id="addBand" type="text" name="addBand" value=""
               title="Название Группы" list="bandslists" required>
        <datalist id="bandslists">
            <c:forEach items="${bands}" var="band">
                <input type="hidden" id="addBandId" name="bandId" value=${band.id}>
                <option value=${band.name}>
                </c:forEach>
        </datalist>
    <!-- <div style="margin-left: 12%" id="addBandName"></div> -->
    <br/><br/>
    ID Альбома:<br/>
    <input style="float: left;" onchange="getNameAlbum(true)" id="addAlbumId" type="text" name="albumId" value=""
           title="ID Альбома" required>
    <div style="margin-left: 12%" id="addAlbumName"></div>
    <br/><br/>
    ID Жанра:<br/>
    <input style="float: left;" onchange="getNameGenre(true)" id="addGenreId" type="text" name="genreId" value=""
           title="ID Жанра"  required>
    <div style="margin-left: 12%" id="addGenreName"></div>
    <br/><br/>
    <input class="input-add" type="submit" style="margin-left: 1.3%" value="Добавить"><br/>
</form>
<hr/>
<form action="songs" method="POST" class="search">
    <input type="hidden" name="action" value="search"/>
    <input type="search" name="searchName" placeholder="поиск" class="input" required/>
    <input type="submit" value="" class="submit" />
</form>
<script>
    function getNameBand(isAdd) {
        var bandId = 0;
        if (isAdd) {
            bandId = document.getElementById("addBandId").value;
            document.getElementById("addBandName").innerHTML = '';
        }
        else {
            bandId = document.getElementById("selectedBandId").value;
            document.getElementById("selectedBandName").innerHTML = '';
        }
        <c:forEach items="${bands}" var="album">
        var servletBandId = '${album.id}';
        if (bandId == servletBandId)
            if (isAdd) document.getElementById("addBandName").innerHTML = '${album.name}';
            else {
                document.getElementById("selectedBandName").innerHTML = '${album.name}';
                document.getElementById("refBandId").value = '${album.id}';
            }
        </c:forEach>
    }

    function getNameGenre(isAdd) {
        var genreId = 0;
        if (isAdd) {
            genreId = document.getElementById("addGenreId").value;
            document.getElementById("addGenreName").innerHTML = '';
        }
        else {
            genreId = document.getElementById("selectedGenreId").value;
            document.getElementById("selectedGenreName").innerHTML = '';
        }
        <c:forEach items="${genres}" var="song">
        var servletGenreId = '${song.id}';
        if (genreId == servletGenreId)
            if (isAdd) document.getElementById("addGenreName").innerHTML = '${song.name}';
            else {
                document.getElementById("selectedGenreName").innerHTML = '${song.name}';
                document.getElementById("refGenreId").value = '${song.id}';
            }
        </c:forEach>
    }

    function getNameAlbum(isAdd) {
        var albumId = 0;
        if (isAdd) {
            albumId = document.getElementById("addAlbumId").value;
            document.getElementById("addAlbumName").innerHTML = '';
        }
        else {
            albumId = document.getElementById("selectedAlbumId").value;
            document.getElementById("selectedAlbumName").innerHTML = '';
        }
        <c:forEach items="${albums}" var="album">
        var servletAlbumId = '${album.id}';
        if (albumId == servletAlbumId)
            if (isAdd) document.getElementById("addAlbumName").innerHTML = '${album.name}';
            else {
                document.getElementById("selectedAlbumName").innerHTML = '${album.name}';
                document.getElementById("refAlbumId").value = '${album.id}';
            }
        </c:forEach>
    }
</script>
<body>
<table style="float: left;" cellpadding="9">
    <thead>
    <tr style="background-color: royalblue; color: white">
        <th>ID Группы</th>
        <th colspan="2">Название песни</th>
    </tr>
    </thead>
    <c:forEach items="${songs}" var="viewedSong">
        <tr style="background-color: lavender" id=${viewedSong.id}>
            <td align="center">
                <button class="input-transparent" style="cursor: pointer"
                        onclick="redirecting('/bands?id=' + '${viewedSong.bandId}');">${viewedSong.bandId}</button>
            </td>
            <td align="center">
                <button class="input-transparent" style="cursor: pointer"
                        onclick="redirecting('/songs?id=' + '${viewedSong.id}');">${viewedSong.name}</button>
            </td>
            <td align="center">
                <form action="songs" method="POST">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value='${viewedSong.id}'>
                    <input type="image" src="/images/del.png" width="25" height="25"
                           alt="УДАЛИТЬ" title="Удалить">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<table id="selectedSong" style="margin-left: 300px" cellpadding="9">
    <thead>
    <tr style="background-color: royalblue; color: white">
        <th align="center">Параметр</th>
        <th colspan="2" align="center">Значение</th>
    </tr>
    </thead>
    <tr style="background-color: lavender">
        <td align="center">Идентификатор</td>
        <td colspan="2" align="center" id="selectedId">${song.id}</td>
    </tr>
    <tr style="background-color: lavender">
        <td align="center">Название</td>
        <td colspan="2"><input class="input-transparent" type="text" id="selectedName" value="${song.name}"></td>
    </tr>
    <tr style="background-color: lavender">
        <td align="center">Длительность</td>
        <td colspan="2"><input class="input-transparent" type="text" id="selectedTime" value="${song.timeToString()}">
        </td>
    </tr>
    <tr style="background-color: lavender">
        <td align="center">ID Группы</td>
        <td><input class="input-transparent" onchange="getNameBand(false)" type="text" id="selectedBandId"
                   value="${song.bandId}"></td>
        <td id="selectedBandName">${bandName}</td>
        <c:if test="${song.bandId > 0}">
            <td>
                <form action="bands" method="GET">
                    <input type="hidden" id="refBandId" name="id" value="${song.bandId}">
                    <input type="image" src="/images/ref.png" width="15" height="25" title="Перейти">
                </form>
            </td>
        </c:if>
    </tr>
    <tr style="background-color: lavender">
        <td align="center">ID Альбома</td>
        <td><input class="input-transparent" onchange="getNameAlbum(false)" type="text" id="selectedAlbumId"
                   value="${song.albumId}"></td>
        <td id="selectedAlbumName">${albumName}</td>
        <c:if test="${song.albumId > 0}">
            <td>
                <form action="albums" method="GET">
                    <input type="hidden" id="refAlbumId" name="id" value="${song.albumId}">
                    <input type="image" src="/images/ref.png" width="15" height="25" title="Перейти">
                </form>
            </td>
        </c:if>
    </tr>
    <tr style="background-color: lavender">
        <td align="center">ID Жанра</td>
        <td><input class="input-transparent" onchange="getNameGenre(false)" type="text" id="selectedGenreId"
                   value="${song.genreId}"></td>
        <td id="selectedGenreName">${genreName}</td>
        <c:if test="${song.genreId > 0}">
            <td>
                <form action="genres" method="GET">
                    <input type="hidden" id="refGenreId" name="id" value="${song.genreId}">
                    <input type="image" src="/images/ref.png" width="15" height="25" title="Перейти">
                </form>
            </td>
        </c:if>
    </tr>
</table>
<form action="songs" method="POST">
    <input type="hidden" name="action" value="update">
    <input type="hidden" id="updatedId" name="id" value="">
    <input type="hidden" id="updatedName" name="name" value="">
    <input type="hidden" id="updatedTime" name="time" value="">
    <input type="hidden" id="updatedBandId" name="bandId" value="">
    <input type="hidden" id="updatedAlbumId" name="albumId" value="">
    <input type="hidden" id="updatedGenreId" name="genreId" value="">
    <input class="input-add" onclick="loadSong()" style="margin-left: 30%" type="submit" value="Сохранить">
</form>
</body>
</html>