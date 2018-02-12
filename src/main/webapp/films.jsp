<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles.css"/><%--
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>--%>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/jquery.maskedinput.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts.js"></script>

<html>
<head>
    <title>Фильмы</title>
</head>

<body class="blue-grad" onload="hideEmptyRedirects()">
    <div class="border">
        <ul class="navigation-bar">
            <li class="navigation-bar" onclick="redirecting('/index.jsp')">Главная</li>
            <li class="navigation-bar-active">Фильмы</li>
            <li class="navigation-bar" onclick="redirecting('/franchises')">Франшизы</li>
            <li class="navigation-bar" onclick="redirecting('/producers')">Режиссеры</li>
            <li class="navigation-bar" onclick="redirecting('/genres')">Жанры</li>
        </ul>
    </div>
    <div class="frame">
        <div class="frame-search">
            <form action="" method="post" class="search" id="search_form">
                <input type="search" id="search_string" placeholder="поиск" class="search-input" required />
                <input type="button" id="search_button" value="" class="search-submit" onclick="searchFilms()"/>
            </form>

            <div class="extended-search" onclick="redirecting('#openExtendedSearch')"></div>
            <div id="openExtendedSearch" class="info-dialog">
                <div>
                    <div class="info-close" title="Закрыть" onclick="redirecting('#close')"></div>
                    <div class="info-title">Фильтр</div>
                    <table style="width: 300px" cellpadding="9">
                        <tr>
                            <td class="info-name">Название:</td>
                            <td colspan="3"><input id="filmNameFilter" class="info-value" value=""></td>
                        </tr>
                        <tr>
                            <td class="info-name">Длительность:</td>
                            <td colspan="3"><input id="filmTimeFilter" class="info-value" value=""></td>
                        </tr>
                        <tr>
                            <td class="info-name">Франшиза:</td>
                            <td><input id="franchiseNameFilter" class="info-value" style="width: 200px" value=""></td>
                        </tr>
                        <tr>
                            <td class="info-name">Режиссер:</td>
                            <td><input id="producerNameFilter" class="info-value" style="width: 200px" value=""></td>
                        </tr>
                        <tr>
                            <td class="info-name">Жанр:</td>
                            <td><input id="genreNameFilter" class="info-value" style="width: 200px" value=""></td>
                        </tr>
                        <tr>
                            <td colspan="4"><button class="save" onclick="extendedSearchFilms()">Поиск</button></td>
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
                            <td colspan="3"><input id="addFilmName" class="info-value" value=""></td>
                        </tr>
                        <tr>
                            <td class="info-name">Длительность:</td>
                            <td colspan="3"><input id="addFilmTime" class="info-value" value=""></td>
                        </tr>
                        <tr>
                            <td class="info-name">Франшиза:</td>
                            <td><input id="addFranchiseId" class="info-value" style="width: 30px" onchange="getAddName('addFranchiseId', 'addFranchiseName', 'franchises')" value=""></td>
                            <td><input id="addFranchiseName" readonly class="info-value-readonly" style="width: 150px" value=""/></td>
                        </tr>
                        <tr>
                            <td class="info-name">Режиссер:</td>
                            <td><input id="addProducerId" class="info-value" style="width: 30px" onchange="getAddName('addProducerId', 'addProducerName', 'producers')" value=""></td>
                            <td><input id="addProducerName" readonly class="info-value-readonly" style="width: 150px" value=""/></td>
                        </tr>
                        <tr>
                            <td class="info-name">Жанр:</td>
                            <td><input id="addGenreId" class="info-value" style="width: 30px" onchange="getAddName('addGenreId', 'addGenreName', 'genres')" value=""></td>
                            <td><input id="addGenreName" readonly class="info-value-readonly" style="width: 150px" value=""/></td>
                        </tr>
                        <tr>
                            <td colspan="4"><button class="save" onclick="addFilm()">Добавить</button></td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>

        <div id="main" style="margin: 0px 5% 0% 5%;">
            <c:forEach items="${films}" var="film">
                <div id="${film.id}" class="rectangle">
                    <div class="data-element" onclick="openSelectedFilm('#openInfo', ${film.id})">
                        <div class="data-index">${film.id}</div>
                        <div class="data-name">${film.filmName}</div>
                        <div class="data-time">${film.timeToString()}</div>
                    </div>
                    <div class="data-delete" onclick="deleteFilm('${film.id}')"></div>
                </div>
            </c:forEach>

            <div id="openInfo" class="info-dialog">
                <div>
                    <div class="info-close" title="Закрыть" onclick="redirecting('#close')"></div>
                    <div class="info-title">Информация</div>

                    <table style="width: 300px" cellpadding="9">
                        <tr>
                            <td class="info-name">Идентификатор:</td>
                            <td colspan="3"><input readonly id="selectedFilmId" class="info-value-readonly" value="${selectedFilm.id}"/></td>
                        </tr>
                        <tr>
                            <td class="info-name">Название:</td>
                            <td colspan="3"><input id="selectedFilmName" class="info-value" value="${selectedFilm.filmName}"></td>
                        </tr>
                        <tr>
                            <td class="info-name">Длительность:</td>
                            <td colspan="3"><input id="selectedFilmTime" class="info-value" value="${selectedFilm.timeToString()}"></td>
                        </tr>
                        <tr>
                            <td class="info-name">Франшиза:</td>
                            <td><input id="selectedFranchiseId" class="info-value" onchange="getSelectedName('franchiseRedirect', 'selectedFranchiseId', 'selectedFranchiseName', 'franchises')" style="width: 30px" value="${selectedFilm.franchiseId}"></td>
                            <td><input id="selectedFranchiseName" readonly class="info-value-readonly" style="width: 120px" value="${franchiseName}"/></td>
                            <td><div id="franchiseRedirect" class="info-redirect" onclick="redirectingOnSelected('franchises', 'selectedFranchiseId')">></div></td>
                        </tr>
                        <tr>
                            <td class="info-name">Режиссер:</td>
                            <td><input id="selectedProducerId" class="info-value" onchange="getSelectedName('producerRedirect', 'selectedProducerId', 'selectedProducerName', 'producers')" style="width: 30px" value="${selectedFilm.producerId}"></td>
                            <td><input id="selectedProducerName" readonly class="info-value-readonly" style="width: 120px" value="${producerName}"/></td>
                            <td><div id="producerRedirect" class="info-redirect" onclick="redirectingOnSelected('producers', 'selectedProducerId')">></div></td>
                        </tr>
                        <tr>
                            <td class="info-name">Жанр:</td>
                            <td><input id="selectedGenreId" class="info-value" onchange="getSelectedName('genreRedirect', 'selectedGenreId', 'selectedGenreName', 'genres')" style="width: 30px" value="${selectedFilm.genreId}"></td>
                            <td><input id="selectedGenreName" readonly class="info-value-readonly" style="width: 120px" value="${genreName}"/></td>
                            <td><div id="genreRedirect" class="info-redirect" onclick="redirectingOnSelected('genres', 'selectedGenreId')">></div></td>
                        </tr>
                        <tr>
                            <td colspan="4"><button class="save" onclick="updateFilm()">Сохранить</button></td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="border"></div>
</body>
</html>
