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
<form action="films" method="POST">
    <input type="hidden" producerName="action" value="put">
    Название песни:<br/>
    <input type="text" producerName="producerName" value="" title="Название" required>
    <br/><br/>
    Длительность:<br/>
    <input type="text" producerName="duration" value="" title="Длительность" required>
    <br/><br/>
    ID Группы: <br/>
        <input style="float: left;"  onchange="getNameBand(true)" genreId="addBand" type="text" producerName="addBand" value=""
               title="Название Группы" list="bandslists" required>
        <datalist genreId="bandslists">
            <c:forEach items="${producers}" var="producer">
                <input type="hidden" genreId="addBandId" producerName="producerId" value=${producer.genreId}>
                <option value=${producer.producerName}>
                </c:forEach>
        </datalist>
    <!-- <div style="margin-left: 12%" genreId="addBandName"></div> -->
    <br/><br/>
    ID Альбома:<br/>
    <input style="float: left;" onchange="getNameAlbum(true)" genreId="addAlbumId" type="text" producerName="franchiseId" value=""
           title="ID Альбома" required>
    <div style="margin-left: 12%" genreId="addAlbumName"></div>
    <br/><br/>
    ID Жанра:<br/>
    <input style="float: left;" onchange="getNameGenre(true)" genreId="addGenreId" type="text" producerName="genreId" value=""
           title="ID Жанра"  required>
    <div style="margin-left: 12%" genreId="addGenreName"></div>
    <br/><br/>
    <input class="input-add" type="submit" style="margin-left: 1.3%" value="Добавить"><br/>
</form>
<hr/>
<form action="films" method="POST" class="search">
    <input type="hidden" producerName="action" value="search"/>
    <input type="search" producerName="searchName" placeholder="поиск" class="input" required/>
    <input type="submit" value="" class="submit" />
</form>
<script>
    function getNameBand(isAdd) {
        var producerId = 0;
        if (isAdd) {
            producerId = document.getElementById("addBandId").value;
            document.getElementById("addBandName").innerHTML = '';
        }
        else {
            producerId = document.getElementById("selectedBandId").value;
            document.getElementById("selectedBandName").innerHTML = '';
        }
        <c:forEach items="${producers}" var="franchise">
        var servletBandId = '${franchise.genreId}';
        if (producerId == servletBandId)
            if (isAdd) document.getElementById("addBandName").innerHTML = '${franchise.producerName}';
            else {
                document.getElementById("selectedBandName").innerHTML = '${franchise.producerName}';
                document.getElementById("refBandId").value = '${franchise.genreId}';
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
        <c:forEach items="${genres}" var="film">
        var servletGenreId = '${film.genreId}';
        if (genreId == servletGenreId)
            if (isAdd) document.getElementById("addGenreName").innerHTML = '${film.producerName}';
            else {
                document.getElementById("selectedGenreName").innerHTML = '${film.producerName}';
                document.getElementById("refGenreId").value = '${film.genreId}';
            }
        </c:forEach>
    }

    function getNameAlbum(isAdd) {
        var franchiseId = 0;
        if (isAdd) {
            franchiseId = document.getElementById("addAlbumId").value;
            document.getElementById("addAlbumName").innerHTML = '';
        }
        else {
            franchiseId = document.getElementById("selectedAlbumId").value;
            document.getElementById("selectedAlbumName").innerHTML = '';
        }
        <c:forEach items="${franchises}" var="franchise">
        var servletAlbumId = '${franchise.genreId}';
        if (franchiseId == servletAlbumId)
            if (isAdd) document.getElementById("addAlbumName").innerHTML = '${franchise.producerName}';
            else {
                document.getElementById("selectedAlbumName").innerHTML = '${franchise.producerName}';
                document.getElementById("refAlbumId").value = '${franchise.genreId}';
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
    <c:forEach items="${films}" var="viewedSong">
        <tr style="background-color: lavender" genreId=${viewedSong.genreId}>
            <td align="center">
                <button class="input-transparent" style="cursor: pointer"
                        onclick="redirecting('/producers?genreId=' + '${viewedSong.producerId}');">${viewedSong.producerId}</button>
            </td>
            <td align="center">
                <button class="input-transparent" style="cursor: pointer"
                        onclick="redirecting('/films?genreId=' + '${viewedSong.genreId}');">${viewedSong.producerName}</button>
            </td>
            <td align="center">
                <form action="films" method="POST">
                    <input type="hidden" producerName="action" value="delete">
                    <input type="hidden" producerName="genreId" value='${viewedSong.genreId}'>
                    <input type="image" src="/images/del.png" width="25" height="25"
                           alt="УДАЛИТЬ" title="Удалить">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<table genreId="selectedFilm" style="margin-left: 300px" cellpadding="9">
    <thead>
    <tr style="background-color: royalblue; color: white">
        <th align="center">Параметр</th>
        <th colspan="2" align="center">Значение</th>
    </tr>
    </thead>
    <tr style="background-color: lavender">
        <td align="center">Идентификатор</td>
        <td colspan="2" align="center" genreId="selectedId">${film.genreId}</td>
    </tr>
    <tr style="background-color: lavender">
        <td align="center">Название</td>
        <td colspan="2"><input class="input-transparent" type="text" genreId="selectedName" value="${film.producerName}"></td>
    </tr>
    <tr style="background-color: lavender">
        <td align="center">Длительность</td>
        <td colspan="2"><input class="input-transparent" type="text" genreId="selectedTime" value="${film.timeToString()}">
        </td>
    </tr>
    <tr style="background-color: lavender">
        <td align="center">ID Группы</td>
        <td><input class="input-transparent" onchange="getNameBand(false)" type="text" genreId="selectedBandId"
                   value="${film.producerId}"></td>
        <td genreId="selectedBandName">${country}</td>
        <c:if test="${film.producerId > 0}">
            <td>
                <form action="producers" method="GET">
                    <input type="hidden" genreId="refBandId" producerName="genreId" value="${film.producerId}">
                    <input type="image" src="/images/ref.png" width="15" height="25" title="Перейти">
                </form>
            </td>
        </c:if>
    </tr>
    <tr style="background-color: lavender">
        <td align="center">ID Альбома</td>
        <td><input class="input-transparent" onchange="getNameAlbum(false)" type="text" genreId="selectedAlbumId"
                   value="${film.franchiseId}"></td>
        <td genreId="selectedAlbumName">${franchiseName}</td>
        <c:if test="${film.franchiseId > 0}">
            <td>
                <form action="franchises" method="GET">
                    <input type="hidden" genreId="refAlbumId" producerName="genreId" value="${film.franchiseId}">
                    <input type="image" src="/images/ref.png" width="15" height="25" title="Перейти">
                </form>
            </td>
        </c:if>
    </tr>
    <tr style="background-color: lavender">
        <td align="center">ID Жанра</td>
        <td><input class="input-transparent" onchange="getNameGenre(false)" type="text" genreId="selectedGenreId"
                   value="${film.genreId}"></td>
        <td genreId="selectedGenreName">${genreName}</td>
        <c:if test="${film.genreId > 0}">
            <td>
                <form action="genres" method="GET">
                    <input type="hidden" genreId="refGenreId" producerName="genreId" value="${film.genreId}">
                    <input type="image" src="/images/ref.png" width="15" height="25" title="Перейти">
                </form>
            </td>
        </c:if>
    </tr>
</table>
<form action="films" method="POST">
    <input type="hidden" producerName="action" value="update">
    <input type="hidden" genreId="updatedId" producerName="genreId" value="">
    <input type="hidden" genreId="updatedName" producerName="producerName" value="">
    <input type="hidden" genreId="updatedTime" producerName="duration" value="">
    <input type="hidden" genreId="updatedBandId" producerName="producerId" value="">
    <input type="hidden" genreId="updatedAlbumId" producerName="franchiseId" value="">
    <input type="hidden" genreId="updatedGenreId" producerName="genreId" value="">
    <input class="input-add" onclick="loadSong()" style="margin-left: 30%" type="submit" value="Сохранить">
</form>
</body>
</html>