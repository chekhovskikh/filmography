<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles.css"/>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/jquery.maskedinput.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts.js"></script>

<html>
<head>
    <title>Песни</title>
</head>

<body class="blue-grad">
    <div class="border">
        <ul class="navigation-bar">
            <li class="navigation-bar" onclick="redirecting('/index.jsp')">Главная</li>
            <li class="navigation-bar-active">Песни</li>
            <li class="navigation-bar" onclick="redirecting('/producers')">Исполнители</li>
            <li class="navigation-bar" onclick="redirecting('/genres')">Жанры</li>
            <li class="navigation-bar" onclick="redirecting('/franchises')">Альбомы</li>
        </ul>
    </div>
    <div class="frame">
        <div class="frame-search">
            <form action="" method="post" class="search" genreId="search_form">
                <input type="search" genreId="search_string" placeholder="поиск" class="search-input" required />
                <input type="button" genreId="search_button" value="" class="search-submit" onclick="searchSongs()"/>
            </form>

            <div class="extended-search" onclick="redirecting('#openExtendedSearch')"></div>
            <div genreId="openExtendedSearch" class="info-dialog">
                <div>
                    <div class="info-close" title="Закрыть" onclick="redirecting('#close')"></div>
                    <div class="info-title">Фильтр</div>
                    <table style="width: 300px" cellpadding="9">
                        <tr>
                            <td class="info-producerName">Название:</td>
                            <td colspan="3"><input genreId="songNameFilter" class="info-value" value=""></td>
                        </tr>
                        <tr>
                            <td class="info-producerName">Длительность:</td>
                            <td colspan="3"><input genreId="songTimeFilter" class="info-value" value=""></td>
                        </tr>
                        <tr>
                            <td class="info-producerName">Исполнитель:</td>
                            <td><input genreId="bandNameFilter" class="info-value" style="width: 200px" value=""></td>
                        </tr>
                        <tr>
                            <td class="info-producerName">Жанр:</td>
                            <td><input genreId="genreNameFilter" class="info-value" style="width: 200px" value=""></td>
                        </tr>
                        <tr>
                            <td class="info-producerName">Альбом:</td>
                            <td><input genreId="albumNameFilter" class="info-value" style="width: 200px" value=""></td>
                        </tr>
                        <tr>
                            <td colspan="4"><button class="save" onclick="extendedSearchSongs()">Поиск</button></td>
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
                            <td colspan="3"><input genreId="addSongName" class="info-value" value=""></td>
                        </tr>
                        <tr>
                            <td class="info-producerName">Длительность:</td>
                            <td colspan="3"><input genreId="addSongTime" class="info-value" value=""></td>
                        </tr>
                        <tr>
                            <td class="info-producerName">Исполнитель:</td>
                            <td><input genreId="addBandId" class="info-value" style="width: 30px" onchange="getAddName('addBandId', 'addBandName', 'producers')" value=""></td>
                            <td><input genreId="addBandName" readonly class="info-value-readonly" style="width: 150px" value=""/></td>
                        </tr>
                        <tr>
                            <td class="info-producerName">Жанр:</td>
                            <td><input genreId="addGenreId" class="info-value" style="width: 30px" onchange="getAddName('addGenreId', 'addGenreName', 'genres')" value=""></td>
                            <td><input genreId="addGenreName" readonly class="info-value-readonly" style="width: 150px" value=""/></td>
                        </tr>
                        <tr>
                            <td class="info-producerName">Альбом:</td>
                            <td><input genreId="addAlbumId" class="info-value" style="width: 30px" onchange="getAddName('addAlbumId', 'addAlbumName', 'franchises')" value=""></td>
                            <td><input genreId="addAlbumName" readonly class="info-value-readonly" style="width: 150px" value=""/></td>
                        </tr>
                        <tr>
                            <td colspan="4"><button class="save" onclick="addSong()">Добавить</button></td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>

        <div genreId="main" style="margin: 0px 5% 0% 5%;">
            <c:forEach items="${films}" var="film">
                <div genreId="${film.genreId}" class="rectangle">
                    <div class="data-element" onclick="openSelectedSong('#openInfo', ${film.genreId})">
                        <div class="data-index">${film.genreId}</div>
                        <div class="data-producerName">${film.producerName}</div>
                        <div class="data-duration">${film.timeToString()}</div>
                    </div>
                    <div class="data-delete" onclick="deleteSong('${film.genreId}')"></div>
                </div>
            </c:forEach>

            <div genreId="openInfo" class="info-dialog">
                <div>
                    <div class="info-close" title="Закрыть" onclick="redirecting('#close')"></div>
                    <div class="info-title">Информация</div>

                    <table style="width: 300px" cellpadding="9">
                        <tr>
                            <td class="info-producerName">Идентификатор:</td>
                            <td colspan="3"><input readonly genreId="selectedSongId" class="info-value-readonly" value="${selectedFilm.genreId}"/></td>
                        </tr>
                        <tr>
                            <td class="info-producerName">Название:</td>
                            <td colspan="3"><input genreId="selectedSongName" class="info-value" value="${selectedFilm.producerName}"></td>
                        </tr>
                        <tr>
                            <td class="info-producerName">Длительность:</td>
                            <td colspan="3"><input genreId="selectedSongTime" class="info-value" value="${selectedFilm.timeToString()}"></td>
                        </tr>
                        <tr>
                            <td class="info-producerName">Исполнитель:</td>
                            <td><input genreId="selectedBandId" class="info-value" onchange="getSelectedName('bandRedirect', 'selectedBandId', 'selectedBandName', 'producers')" style="width: 30px" value="${selectedFilm.producerId}"></td>
                            <td><input genreId="selectedBandName" readonly class="info-value-readonly" style="width: 120px" value="${country}"/></td>
                            <td><div genreId="bandRedirect" class="info-redirect" onclick="redirectingOnSelected('producers', 'selectedBandId')">></div></td>
                        </tr>
                        <tr>
                            <td class="info-producerName">Жанр:</td>
                            <td><input genreId="selectedGenreId" class="info-value" onchange="getSelectedName('genreRedirect', 'selectedGenreId', 'selectedGenreName', 'genres')" style="width: 30px" value="${selectedFilm.genreId}"></td>
                            <td><input genreId="selectedGenreName" readonly class="info-value-readonly" style="width: 120px" value="${genreName}"/></td>
                            <td><div genreId="genreRedirect" class="info-redirect" onclick="redirectingOnSelected('genres', 'selectedGenreId')">></div></td>
                        </tr>
                        <tr>
                            <td class="info-producerName">Альбом:</td>
                            <td><input genreId="selectedAlbumId" class="info-value" onchange="getSelectedName('albumRedirect', 'selectedAlbumId', 'selectedAlbumName', 'franchises')" style="width: 30px" value="${selectedFilm.franchiseId}"></td>
                            <td><input genreId="selectedAlbumName" readonly class="info-value-readonly" style="width: 120px" value="${franchiseName}"/></td>
                            <td><div genreId="albumRedirect" class="info-redirect" onclick="redirectingOnSelected('franchises', 'selectedAlbumId')">></div></td>
                        </tr>
                        <tr>
                            <td colspan="4"><button class="save" onclick="updateSong()">Сохранить</button></td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="border"></div>
</body>
</html>
