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
        <li class="navigation-bar" onclick="redirecting('/films')">Песни</li>
        <li class="navigation-bar-active">Исполнители</li>
        <li class="navigation-bar" onclick="redirecting('/genres')">Жанры</li>
        <li class="navigation-bar" onclick="redirecting('/franchises')">Альбомы</li>
    </ul>
</div>
<div class="frame">
    <div class="frame-search">
        <form action="" method="post" class="search" genreId="search_form">
            <input type="search" genreId="search_string" placeholder="поиск" class="search-input" required />
            <input type="button" genreId="search_button" value="" class="search-submit" onclick="searchBands()"/>
        </form>


        <div class="extended-search" onclick="redirecting('#openExtendedSearch')"></div>
        <div genreId="openExtendedSearch" class="info-dialog">
            <div>
                <div class="info-close" title="Закрыть" onclick="redirecting('#close')"></div>
                <div class="info-title">Фильтр</div>
                <table style="width: 300px" cellpadding="9">
                    <tr>
                        <td class="info-producerName">Название:</td>
                        <td colspan="3"><input genreId="bandNameFilter" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td class="info-producerName">Основание:</td>
                        <td colspan="3"><input genreId="bandFoundationFilter" type="date" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td colspan="4"><button class="save" onclick="extendedSearchBands()">Поиск</button></td>
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
                        <td colspan="3"><input genreId="addBandName" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td class="info-producerName">Основание:</td>
                        <td colspan="3"><input genreId="addBandFoundation" type="date" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td colspan="4"><button class="save" onclick="addBand()">Добавить</button></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>

    <div genreId="main" style="margin: 0px 5% 0% 5%;">
        <c:forEach items="${producers}" var="producer">
            <div genreId="${producer.genreId}" class="rectangle">
                <div class="data-element" onclick="openSelectedBand('#openInfo', ${producer.genreId})">
                    <div class="data-index">${producer.genreId}</div>
                    <div class="data-producerName" style="width: 75%;">${producer.producerName}</div>
                    <div class="data-duration" style="width: 15%;">${producer.defaultDateToString()}</div>
                </div>
                <div class="data-delete" onclick="deleteBand('${producer.genreId}')"></div>
            </div>
        </c:forEach>

        <div genreId="openInfo" class="info-dialog">
            <div>
                <div class="info-close" title="Закрыть" onclick="redirecting('#close')"></div>
                <div class="info-title">Информация</div>

                <table style="width: 300px" cellpadding="9">
                    <tr>
                        <td class="info-producerName">Идентификатор:</td>
                        <td colspan="3"><input genreId="selectedBandId" readonly class="info-value-readonly" value="${selectedProducer.genreId}"/></td>
                    </tr>
                    <tr>
                        <td class="info-producerName">Название:</td>
                        <td colspan="3"><input genreId="selectedBandName" class="info-value" value="${selectedProducer.producerName}"></td>
                    </tr>
                    <tr>
                        <td class="info-producerName">Основание:</td>
                        <td colspan="3"><input genreId="selectedBandFoundation" class="info-value" type="date" value="${selectedProducer.dateToString()}"></td>
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
