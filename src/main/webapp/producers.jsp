<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles.css"/><%--
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>--%>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/jquery.maskedinput.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts.js"></script>

<html>
<head>
    <title>Режиссеры</title>
</head>

<body class="blue-grad">
<div class="border">
    <ul class="navigation-bar">
        <li class="navigation-bar" onclick="redirecting('/index.jsp')">Главная</li>
        <li class="navigation-bar" onclick="redirecting('/films')">Фильмы</li>
        <li class="navigation-bar" onclick="redirecting('/franchises')">Франщизы</li>
        <li class="navigation-bar-active">Режиссеры</li>
        <li class="navigation-bar" onclick="redirecting('/genres')">Жанры</li>
    </ul>
</div>
<div class="frame">
    <div class="frame-search">
        <form action="" method="post" class="search" id="search_form">
            <input type="search" id="search_string" placeholder="поиск" class="search-input" required />
            <input type="button" id="search_button" value="" class="search-submit" onclick="searchProducers()"/>
        </form>


        <div class="extended-search" onclick="redirecting('#openExtendedSearch')"></div>
        <div id="openExtendedSearch" class="info-dialog">
            <div>
                <div class="info-close" title="Закрыть" onclick="redirecting('#close')"></div>
                <div class="info-title">Фильтр</div>
                <table style="width: 300px" cellpadding="9">
                    <tr>
                        <td class="info-name">Имя:</td>
                        <td colspan="3"><input id="producerNameFilter" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td class="info-name">Дата Рождения:</td>
                        <td colspan="3"><input id="producerBirthdateFilter" type="date" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td class="info-name">Гражданство:</td>
                        <td colspan="3"><input id="producerCitizenshipFilter" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td colspan="4"><button class="save" onclick="extendedSearchProducers()">Поиск</button></td>
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
                        <td class="info-name">Имя:</td>
                        <td colspan="3"><input id="addProducerName" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td class="info-name">Дата Рождение:</td>
                        <td colspan="3"><input id="addProducerBirthdate" type="date" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td class="info-name">Гражданство:</td>
                        <td colspan="3"><input id="addProducerCitizenship" class="info-value" value=""></td>
                    </tr>
                    <tr>
                        <td colspan="4"><button class="save" onclick="addProducer()">Добавить</button></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>

    <div id="main" style="margin: 0px 5% 0% 5%;">
        <c:forEach items="${producers}" var="producer">
            <div id="${producer.id}" class="rectangle">
                <div class="data-element" onclick="openSelectedProducer('#openInfo', ${producer.id})">
                    <div class="data-index">${producer.id}</div>
                    <div class="data-name" style="width: 75%;">${producer.producerName}</div>
                    <div class="data-time" style="width: 15%;">${producer.defaultDateToString()}</div>
                </div>
                <div class="data-delete" onclick="deleteProducer('${producer.id}')"></div>
            </div>
        </c:forEach>

        <div id="openInfo" class="info-dialog">
            <div>
                <div class="info-close" title="Закрыть" onclick="redirecting('#close')"></div>
                <div class="info-title">Информация</div>

                <table style="width: 300px" cellpadding="9">
                    <tr>
                        <td class="info-name">Идентификатор:</td>
                        <td colspan="3"><input id="selectedProducerId" readonly class="info-value-readonly" value="${selectedProducer.id}"/></td>
                    </tr>
                    <tr>
                        <td class="info-name">Имя:</td>
                        <td colspan="3"><input id="selectedProducerName" class="info-value" value="${selectedProducer.producerName}"></td>
                    </tr>
                    <tr>
                        <td class="info-name">Дата Рождения:</td>
                        <td colspan="3"><input id="selectedProducerBirthdate" class="info-value" type="date" value="${selectedProducer.dateToString()}"></td>
                    </tr>
                    <tr>
                        <td class="info-name">Гражданство:</td>
                        <td colspan="3"><input id="selectedProducerCitizenship" class="info-value" value="${selectedProducer.citizenship}"></td>
                    </tr>
                    <tr>
                        <td colspan="4"><button class="save" onclick="updateProducer()">Сохранить</button></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
<div class="border"></div>
</body>
</html>
