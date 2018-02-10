<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles.css"/>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/jquery.maskedinput.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts.js"></script>

<html>
<head>
    <title>Альбомы</title>
</head>

<body class="blue-grad" onload="randomCover()">
<div class="border">
    <ul class="navigation-bar">
        <li class="navigation-bar" onclick="redirecting('/index.jsp')">Главная</li>
        <li class="navigation-bar" onclick="redirecting('/songs')">Песни</li>
        <li class="navigation-bar" onclick="redirecting('/bands')">Исполнители</li>
        <li class="navigation-bar" onclick="redirecting('/genres')">Жанры</li>
        <li class="navigation-bar-active">Альбомы</li>
    </ul>
</div>
<div class="frame">
    <div class="frame-search">
        <form action="" method="post" class="search" id="search_form">
            <input type="search" id="search_string" placeholder="поиск" class="search-input" required />
            <input type="button" id="search_button" value="" class="search-submit" onclick="searchAlbums()"/>
        </form>

        <div class="extended-search" onclick="redirecting('#openExtendedSearch')"></div>
        <div id="openExtendedSearch" class="info-dialog">
            <div>
                <div class="info-close" title="Закрыть" onclick="redirecting('#close')"></div>
                <div class="info-title">Фильтр</div>
                <table style="width: 300px" cellpadding="9">
                    <tr>
                        <td class="info-name">Название:</td>
                        <td colspan="3"><input id="albumNameFilter" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td class="info-name">Дата выхода:</td>
                        <td colspan="3"><input id="albumReleaseFilter" type="date" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td class="info-name">Исполнитель:</td>
                        <td><input id="bandNameFilter" class="info-value" style="width: 200px" value=""></td>
                    </tr>
                    <tr>
                        <td colspan="4"><button class="save" onclick="extendedSearchAlbums()">Поиск</button></td>
                    </tr>
                </table>
            </div>
        </div>

        <div class="add" onclick="redirecting('#openAdd')"></div>
        <div id="openAdd" class="info-dialog">
            <div>
                <div class="info-close" title="Закрыть" onclick="redirecting('#close')"></div>
                <div class="info-title">Добавить</div>
                <table style="width: 300px" cellpadding="9">
                    <tr>
                        <td class="info-name">Название:</td>
                        <td colspan="3"><input id="addAlbumName" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td class="info-name">Дата выхода:</td>
                        <td colspan="3"><input id="addAlbumRelease" type="date" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td class="info-name">Исполнитель:</td>
                        <td><input id="addBandId" class="info-value" style="width: 30px" onchange="getAddName('addBandId', 'addBandName', 'bands')" value=""></td>
                        <td><input id="addBandName" readonly class="info-value-readonly" style="width: 150px" value=""/></td>
                    </tr>
                    <tr>
                        <td colspan="4"><button class="save" onclick="addAlbum()">Добавить</button></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>

    <div id="main" style="margin: 0px 0% 0% 8%;">
        <c:forEach items="${albums}" var="album">
            <div id="${album.id}" class="cover">
                <div class="grad-cover"></div>
                <div class="text-delete" onclick="deleteAlbum('${album.id}')">X</div>
                <div class="shadow" onclick="openSelectedAlbum('#openInfo', ${album.id})">
                    <div class="text-name">${album.name}</div>
                    <div class="text-id">${album.id}</div>
                </div>
            </div>
        </c:forEach>

        <div id="openInfo" class="info-dialog">
            <div>
                <div class="info-close" title="Закрыть" onclick="redirecting('#close')"></div>
                <div class="info-title">Информация</div>

                <table style="width: 300px" cellpadding="9">
                    <tr>
                        <td class="info-name">Идентификатор:</td>
                        <td colspan="3"><input readonly id="selectedAlbumId" class="info-value-readonly" value="${selectedAlbum.id}"/></td>
                    </tr>
                    <tr>
                        <td class="info-name">Название:</td>
                        <td colspan="3"><input id="selectedAlbumName" class="info-value" value="${selectedAlbum.name}"></td>
                    </tr>
                    <tr>
                        <td class="info-name">Дата выхода:</td>
                        <td colspan="3"><input id="selectedAlbumRelease" type="date" class="info-value" value="${selectedAlbum.dateToString()}"></td>
                    </tr>
                    <tr>
                        <td class="info-name">Исполнитель:</td>
                        <td><input id="selectedBandId" class="info-value" onchange="getSelectedName('bandRedirect', 'selectedBandId', 'selectedBandName', 'bands')" style="width: 30px" value="${selectedAlbum.bandId}"></td>
                        <td><input id="selectedBandName" readonly class="info-value-readonly" style="width: 120px" value="${bandName}"/></td>
                        <td><div id="bandRedirect" class="info-redirect" onclick="redirectingOnSelected('bands', 'selectedBandId')">></div></td>
                    </tr>
                    <tr>
                        <td colspan="4"><button class="save" onclick="updateAlbum()">Сохранить</button></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
<div class="border"></div>
</body>
</html>
