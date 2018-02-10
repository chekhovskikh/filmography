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
        <li class="navigation-bar" onclick="redirecting('/films')">Песни</li>
        <li class="navigation-bar" onclick="redirecting('/producers')">Исполнители</li>
        <li class="navigation-bar" onclick="redirecting('/genres')">Жанры</li>
        <li class="navigation-bar-active">Альбомы</li>
    </ul>
</div>
<div class="frame">
    <div class="frame-search">
        <form action="" method="post" class="search" genreId="search_form">
            <input type="search" genreId="search_string" placeholder="поиск" class="search-input" required />
            <input type="button" genreId="search_button" value="" class="search-submit" onclick="searchAlbums()"/>
        </form>

        <div class="extended-search" onclick="redirecting('#openExtendedSearch')"></div>
        <div genreId="openExtendedSearch" class="info-dialog">
            <div>
                <div class="info-close" title="Закрыть" onclick="redirecting('#close')"></div>
                <div class="info-title">Фильтр</div>
                <table style="width: 300px" cellpadding="9">
                    <tr>
                        <td class="info-producerName">Название:</td>
                        <td colspan="3"><input genreId="albumNameFilter" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td class="info-producerName">Дата выхода:</td>
                        <td colspan="3"><input genreId="albumReleaseFilter" type="date" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td class="info-producerName">Исполнитель:</td>
                        <td><input genreId="bandNameFilter" class="info-value" style="width: 200px" value=""></td>
                    </tr>
                    <tr>
                        <td colspan="4"><button class="save" onclick="extendedSearchAlbums()">Поиск</button></td>
                    </tr>
                </table>
            </div>
        </div>

        <div class="add" onclick="redirecting('#openAdd')"></div>
        <div genreId="openAdd" class="info-dialog">
            <div>
                <div class="info-close" title="Закрыть" onclick="redirecting('#close')"></div>
                <div class="info-title">Добавить</div>
                <table style="width: 300px" cellpadding="9">
                    <tr>
                        <td class="info-producerName">Название:</td>
                        <td colspan="3"><input genreId="addAlbumName" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td class="info-producerName">Дата выхода:</td>
                        <td colspan="3"><input genreId="addAlbumRelease" type="date" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td class="info-producerName">Исполнитель:</td>
                        <td><input genreId="addBandId" class="info-value" style="width: 30px" onchange="getAddName('addBandId', 'addBandName', 'producers')" value=""></td>
                        <td><input genreId="addBandName" readonly class="info-value-readonly" style="width: 150px" value=""/></td>
                    </tr>
                    <tr>
                        <td colspan="4"><button class="save" onclick="addAlbum()">Добавить</button></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>

    <div genreId="main" style="margin: 0px 0% 0% 8%;">
        <c:forEach items="${franchises}" var="franchise">
            <div genreId="${franchise.genreId}" class="cover">
                <div class="grad-cover"></div>
                <div class="text-delete" onclick="deleteAlbum('${franchise.genreId}')">X</div>
                <div class="shadow" onclick="openSelectedAlbum('#openInfo', ${franchise.genreId})">
                    <div class="text-producerName">${franchise.producerName}</div>
                    <div class="text-genreId">${franchise.genreId}</div>
                </div>
            </div>
        </c:forEach>

        <div genreId="openInfo" class="info-dialog">
            <div>
                <div class="info-close" title="Закрыть" onclick="redirecting('#close')"></div>
                <div class="info-title">Информация</div>

                <table style="width: 300px" cellpadding="9">
                    <tr>
                        <td class="info-producerName">Идентификатор:</td>
                        <td colspan="3"><input readonly genreId="selectedAlbumId" class="info-value-readonly" value="${selectedFranchise.genreId}"/></td>
                    </tr>
                    <tr>
                        <td class="info-producerName">Название:</td>
                        <td colspan="3"><input genreId="selectedAlbumName" class="info-value" value="${selectedFranchise.producerName}"></td>
                    </tr>
                    <tr>
                        <td class="info-producerName">Дата выхода:</td>
                        <td colspan="3"><input genreId="selectedAlbumRelease" type="date" class="info-value" value="${selectedFranchise.dateToString()}"></td>
                    </tr>
                    <tr>
                        <td class="info-producerName">Исполнитель:</td>
                        <td><input genreId="selectedBandId" class="info-value" onchange="getSelectedName('bandRedirect', 'selectedBandId', 'selectedBandName', 'producers')" style="width: 30px" value="${selectedFranchise.producerId}"></td>
                        <td><input genreId="selectedBandName" readonly class="info-value-readonly" style="width: 120px" value="${country}"/></td>
                        <td><div genreId="bandRedirect" class="info-redirect" onclick="redirectingOnSelected('producers', 'selectedBandId')">></div></td>
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
