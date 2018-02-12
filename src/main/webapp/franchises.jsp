<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles.css"/><%--
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>--%>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/jquery.maskedinput.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts.js"></script>

<html>
<head>
    <title>Франшизы</title>
</head>

<body class="blue-grad" onload="randomCover()">
<div class="border">
    <ul class="navigation-bar">
        <li class="navigation-bar" onclick="redirecting('/index.jsp')">Главная</li>
        <li class="navigation-bar" onclick="redirecting('/films')">Фильмы</li>
        <li class="navigation-bar-active">Франшизы</li>
        <li class="navigation-bar" onclick="redirecting('/producers')">Режиссеры</li>
        <li class="navigation-bar" onclick="redirecting('/genres')">Жанры</li>
    </ul>
</div>
<div class="frame">
    <div class="frame-search">
        <form action="" method="post" class="search" id="search_form">
            <input type="search" id="search_string" placeholder="поиск" class="search-input" required />
            <input type="button" id="search_button" value="" class="search-submit" onclick="searchFranchises()"/>
        </form>

        <div class="extended-search" onclick="redirecting('#openExtendedSearch')"></div>
        <div id="openExtendedSearch" class="info-dialog">
            <div>
                <div class="info-close" title="Закрыть" onclick="redirecting('#close')"></div>
                <div class="info-title">Фильтр</div>
                <table style="width: 300px" cellpadding="9">
                    <tr>
                        <td class="info-name">Название:</td>
                        <td colspan="3"><input id="franchiseNameFilter" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td class="info-name">Дата выхода:</td>
                        <td colspan="3"><input id="franchiseReleaseFilter" type="date" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td class="info-name">Страна:</td>
                        <td colspan="3"><input id="franchiseCountryFilter" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td colspan="4"><button class="save" onclick="extendedSearchFranchises()">Поиск</button></td>
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
                        <td colspan="3"><input id="addFranchiseName" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td class="info-name">Дата выхода:</td>
                        <td colspan="3"><input id="addFranchiseRelease" type="date" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td class="info-name">Страна:</td>
                        <td colspan="3"><input id="addFranchiseCountry" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td colspan="4"><button class="save" onclick="addFranchise()">Добавить</button></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>

    <div id="main" style="margin: 0px 0% 0% 8%;">
        <c:forEach items="${franchises}" var="franchise">
            <div id="${franchise.id}" class="cover">
                <div class="grad-cover"></div>
                <div class="text-delete" onclick="deleteFranchise('${franchise.id}')">X</div>
                <div class="shadow" onclick="openSelectedFranchise('#openInfo', ${franchise.id})">
                    <div class="text-name">${franchise.franchiseName}</div>
                    <div class="text-id">${franchise.id}</div>
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
                        <td colspan="3"><input readonly id="selectedFranchiseId" class="info-value-readonly" value="${selectedFranchise.id}"/></td>
                    </tr>
                    <tr>
                        <td class="info-name">Название:</td>
                        <td colspan="3"><input id="selectedFranchiseName" class="info-value" value="${selectedFranchise.franchiseName}"></td>
                    </tr>
                    <tr>
                        <td class="info-name">Дата выхода:</td>
                        <td colspan="3"><input id="selectedFranchiseRelease" type="date" class="info-value" value="${selectedFranchise.dateToString()}"></td>
                    </tr>
                    <tr>
                        <td class="info-name">Страна:</td>
                        <td colspan="3"><input id="selectedFranchiseCountry" class="info-value" value="${selectedFranchise.country}"></td>
                    </tr>
                    <tr>
                        <td colspan="4"><button class="save" onclick="updateFranchise()">Сохранить</button></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
<div class="border"></div>
</body>
</html>
