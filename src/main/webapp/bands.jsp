<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles.css"/>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/jquery.maskedinput.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts.js"></script>

<html>
<head>
    <title>Исполнители</title>
</head>

<body class="blue-grad">
<div class="border">
    <ul class="navigation-bar">
        <li class="navigation-bar" onclick="redirecting('/index.jsp')">Главная</li>
        <li class="navigation-bar" onclick="redirecting('/songs')">Песни</li>
        <li class="navigation-bar-active">Исполнители</li>
        <li class="navigation-bar" onclick="redirecting('/genres')">Жанры</li>
        <li class="navigation-bar" onclick="redirecting('/albums')">Альбомы</li>
    </ul>
</div>
<div class="frame">
    <div class="frame-search">
        <form action="" method="post" class="search" id="search_form">
            <input type="search" id="search_string" placeholder="поиск" class="search-input" required />
            <input type="button" id="search_button" value="" class="search-submit" onclick="searchBands()"/>
        </form>


        <div class="extended-search" onclick="redirecting('#openExtendedSearch')"></div>
        <div id="openExtendedSearch" class="info-dialog">
            <div>
                <div class="info-close" title="Закрыть" onclick="redirecting('#close')"></div>
                <div class="info-title">Фильтр</div>
                <table style="width: 300px" cellpadding="9">
                    <tr>
                        <td class="info-name">Название:</td>
                        <td colspan="3"><input id="bandNameFilter" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td class="info-name">Основание:</td>
                        <td colspan="3"><input id="bandFoundationFilter" type="date" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td colspan="4"><button class="save" onclick="extendedSearchBands()">Поиск</button></td>
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
                        <td colspan="3"><input id="addBandName" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td class="info-name">Основание:</td>
                        <td colspan="3"><input id="addBandFoundation" type="date" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td colspan="4"><button class="save" onclick="addBand()">Добавить</button></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>

    <div id="main" style="margin: 0px 5% 0% 5%;">
        <c:forEach items="${bands}" var="band">
            <div id="${band.id}" class="rectangle">
                <div class="data-element" onclick="openSelectedBand('#openInfo', ${band.id})">
                    <div class="data-index">${band.id}</div>
                    <div class="data-name" style="width: 75%;">${band.name}</div>
                    <div class="data-time" style="width: 15%;">${band.defaultDateToString()}</div>
                </div>
                <div class="data-delete" onclick="deleteBand('${band.id}')"></div>
            </div>
        </c:forEach>

        <div id="openInfo" class="info-dialog">
            <div>
                <div class="info-close" title="Закрыть" onclick="redirecting('#close')"></div>
                <div class="info-title">Информация</div>

                <table style="width: 300px" cellpadding="9">
                    <tr>
                        <td class="info-name">Идентификатор:</td>
                        <td colspan="3"><input id="selectedBandId" readonly class="info-value-readonly" value="${selectedBand.id}"/></td>
                    </tr>
                    <tr>
                        <td class="info-name">Название:</td>
                        <td colspan="3"><input id="selectedBandName" class="info-value" value="${selectedBand.name}"></td>
                    </tr>
                    <tr>
                        <td class="info-name">Основание:</td>
                        <td colspan="3"><input id="selectedBandFoundation" class="info-value" type="date" value="${selectedBand.dateToString()}"></td>
                    </tr>
                    <tr>
                        <td colspan="4"><button class="save" onclick="updateBand()">Сохранить</button></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
<div class="border"></div>
</body>
</html>
