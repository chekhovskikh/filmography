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
            <li class="navigation-bar" onclick="redirecting('/bands')">Исполнители</li>
            <li class="navigation-bar" onclick="redirecting('/genres')">Жанры</li>
            <li class="navigation-bar" onclick="redirecting('/albums')">Альбомы</li>
        </ul>
    </div>
    <div class="frame">
        <div class="frame-search">
            <form action="" method="post" class="search" id="search_form">
                <input type="search" id="search_string" placeholder="поиск" class="search-input" required />
                <input type="button" id="search_button" value="" class="search-submit" onclick="searchSongs()"/>
            </form>

            <div class="extended-search" onclick="redirecting('#openExtendedSearch')"></div>
            <div id="openExtendedSearch" class="info-dialog">
                <div>
                    <div class="info-close" title="Закрыть" onclick="redirecting('#close')"></div>
                    <div class="info-title">Фильтр</div>
                    <table style="width: 300px" cellpadding="9">
                        <tr>
                            <td class="info-name">Название:</td>
                            <td colspan="3"><input id="songNameFilter" class="info-value" value=""></td>
                        </tr>
                        <tr>
                            <td class="info-name">Длительность:</td>
                            <td colspan="3"><input id="songTimeFilter" class="info-value" value=""></td>
                        </tr>
                        <tr>
                            <td class="info-name">Исполнитель:</td>
                            <td><input id="bandNameFilter" class="info-value" style="width: 200px" value=""></td>
                        </tr>
                        <tr>
                            <td class="info-name">Жанр:</td>
                            <td><input id="genreNameFilter" class="info-value" style="width: 200px" value=""></td>
                        </tr>
                        <tr>
                            <td class="info-name">Альбом:</td>
                            <td><input id="albumNameFilter" class="info-value" style="width: 200px" value=""></td>
                        </tr>
                        <tr>
                            <td colspan="4"><button class="save" onclick="extendedSearchSongs()">Поиск</button></td>
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
                            <td colspan="3"><input id="addSongName" class="info-value" value=""></td>
                        </tr>
                        <tr>
                            <td class="info-name">Длительность:</td>
                            <td colspan="3"><input id="addSongTime" class="info-value" value=""></td>
                        </tr>
                        <tr>
                            <td class="info-name">Исполнитель:</td>
                            <td><input id="addBandId" class="info-value" style="width: 30px" onchange="getAddName('addBandId', 'addBandName', 'bands')" value=""></td>
                            <td><input id="addBandName" readonly class="info-value-readonly" style="width: 150px" value=""/></td>
                        </tr>
                        <tr>
                            <td class="info-name">Жанр:</td>
                            <td><input id="addGenreId" class="info-value" style="width: 30px" onchange="getAddName('addGenreId', 'addGenreName', 'genres')" value=""></td>
                            <td><input id="addGenreName" readonly class="info-value-readonly" style="width: 150px" value=""/></td>
                        </tr>
                        <tr>
                            <td class="info-name">Альбом:</td>
                            <td><input id="addAlbumId" class="info-value" style="width: 30px" onchange="getAddName('addAlbumId', 'addAlbumName', 'albums')" value=""></td>
                            <td><input id="addAlbumName" readonly class="info-value-readonly" style="width: 150px" value=""/></td>
                        </tr>
                        <tr>
                            <td colspan="4"><button class="save" onclick="addSong()">Добавить</button></td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>

        <div id="main" style="margin: 0px 5% 0% 5%;">
            <c:forEach items="${songs}" var="song">
                <div id="${song.id}" class="rectangle">
                    <div class="data-element" onclick="openSelectedSong('#openInfo', ${song.id})">
                        <div class="data-index">${song.id}</div>
                        <div class="data-name">${song.name}</div>
                        <div class="data-time">${song.timeToString()}</div>
                    </div>
                    <div class="data-delete" onclick="deleteSong('${song.id}')"></div>
                </div>
            </c:forEach>

            <div id="openInfo" class="info-dialog">
                <div>
                    <div class="info-close" title="Закрыть" onclick="redirecting('#close')"></div>
                    <div class="info-title">Информация</div>

                    <table style="width: 300px" cellpadding="9">
                        <tr>
                            <td class="info-name">Идентификатор:</td>
                            <td colspan="3"><input readonly id="selectedSongId" class="info-value-readonly" value="${selectedSong.id}"/></td>
                        </tr>
                        <tr>
                            <td class="info-name">Название:</td>
                            <td colspan="3"><input id="selectedSongName" class="info-value" value="${selectedSong.name}"></td>
                        </tr>
                        <tr>
                            <td class="info-name">Длительность:</td>
                            <td colspan="3"><input id="selectedSongTime" class="info-value" value="${selectedSong.timeToString()}"></td>
                        </tr>
                        <tr>
                            <td class="info-name">Исполнитель:</td>
                            <td><input id="selectedBandId" class="info-value" onchange="getSelectedName('bandRedirect', 'selectedBandId', 'selectedBandName', 'bands')" style="width: 30px" value="${selectedSong.bandId}"></td>
                            <td><input id="selectedBandName" readonly class="info-value-readonly" style="width: 120px" value="${bandName}"/></td>
                            <td><div id="bandRedirect" class="info-redirect" onclick="redirectingOnSelected('bands', 'selectedBandId')">></div></td>
                        </tr>
                        <tr>
                            <td class="info-name">Жанр:</td>
                            <td><input id="selectedGenreId" class="info-value" onchange="getSelectedName('genreRedirect', 'selectedGenreId', 'selectedGenreName', 'genres')" style="width: 30px" value="${selectedSong.genreId}"></td>
                            <td><input id="selectedGenreName" readonly class="info-value-readonly" style="width: 120px" value="${genreName}"/></td>
                            <td><div id="genreRedirect" class="info-redirect" onclick="redirectingOnSelected('genres', 'selectedGenreId')">></div></td>
                        </tr>
                        <tr>
                            <td class="info-name">Альбом:</td>
                            <td><input id="selectedAlbumId" class="info-value" onchange="getSelectedName('albumRedirect', 'selectedAlbumId', 'selectedAlbumName', 'albums')" style="width: 30px" value="${selectedSong.albumId}"></td>
                            <td><input id="selectedAlbumName" readonly class="info-value-readonly" style="width: 120px" value="${albumName}"/></td>
                            <td><div id="albumRedirect" class="info-redirect" onclick="redirectingOnSelected('albums', 'selectedAlbumId')">></div></td>
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
