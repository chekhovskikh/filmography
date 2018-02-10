<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles.css"/><%--
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>--%>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/jquery.maskedinput.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts.js"></script>

<html>
<head>
    <title>Жанры</title>
</head>

<body class="blue-grad" onload="randomCover()">
<div class="border">
    <ul class="navigation-bar">
        <li class="navigation-bar" onclick="redirecting('/index.jsp')">Главная</li>
        <li class="navigation-bar" onclick="redirecting('/films')">Фильмы</li>
        <li class="navigation-bar" onclick="redirecting('/franchises')">Франшизы</li>
        <li class="navigation-bar" onclick="redirecting('/producers')">Режиссеры</li>
        <li class="navigation-bar-active">Жанры</li>
    </ul>
</div>
<div class="frame">
    <div class="frame-search">
        <form action="" method="post" class="search" id="search_form">
            <input type="search" id="search_string" placeholder="поиск" class="search-input" required />
            <input type="button" id="search_button" value="" class="search-submit" onclick="searchGenres()"/>
        </form>

        <div class="extended-search" onclick="redirecting('#openExtendedSearch')"></div>
        <div id="openExtendedSearch" class="info-dialog">
            <div>
                <div class="info-close" title="Закрыть" onclick="redirecting('#close')"></div>
                <div class="info-title">Фильтр</div>
                <table style="width: 300px" cellpadding="9">
                    <tr>
                        <td class="info-name">Название:</td>
                        <td colspan="3"><input id="genreNameFilter" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td class="info-name">Наджанр:</td>
                        <td colspan="3"><input id="parentNameFilter" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td colspan="4"><button class="save" onclick="extendedSearchGenres()">Поиск</button></td>
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
                        <td colspan="3"><input id="addGenreName" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td class="info-name">Наджанр:</td>
                        <td><input id="addParentId" class="info-value" style="width: 30px" onchange="getAddName('addParentId', 'addParentName', 'genres')" value=""></td>
                        <td><input id="addParentName" readonly class="info-value-readonly" style="width: 150px" value=""/></td>
                    </tr>
                    <tr>
                        <td colspan="4"><button class="save" onclick="addGenre()">Добавить</button></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>

    <div id="main" style="margin: 0px 0% 0% 8%;">
        <c:forEach items="${genres}" var="genre">
            <div id="${genre.id}" class="cover">
                <div class="grad-cover"></div>
                <div class="text-delete" onclick="deleteGenre('${genre.id}')">X</div>
                <div class="shadow" onclick="openSelectedGenre('#openInfo', ${genre.id})">
                    <div class="text-name">${genre.genreName}</div>
                    <div class="text-id">${genre.id}</div>
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
                        <td colspan="3"><input readonly id="selectedGenreId" class="info-value-readonly" value="${selectedGenre.id}"/></td>
                    </tr>
                    <tr>
                        <td class="info-name">Название:</td>
                        <td colspan="3"><input id="selectedGenreName" class="info-value" value="${selectedGenre.genreName}"></td>
                    </tr>
                    <tr>
                        <td class="info-name">Наджанр:</td>
                        <td><input id="selectedParentId" class="info-value" onchange="getSelectedName('parentRedirect', 'selectedParentId', 'selectedParentName', 'genres')" style="width: 30px" value="${selectedGenre.parentId}"></td>
                        <td><input id="selectedParentName" readonly class="info-value-readonly" style="width: 120px" value="${parentName}"/></td>
                        <td><div id="parentRedirect" class="info-redirect" onclick="redirectingOnSelected('genres', 'selectedParentId')">></div></td>
                    </tr>
                    <tr>
                        <td colspan="4"><button class="save" onclick="updateGenre()">Сохранить</button></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
<div class="border"></div>
</body>
</html>
