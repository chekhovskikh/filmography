<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles.css"/><%--
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>--%>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/jquery.maskedinput.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts.js"></script>

<html>
<head>
    <title>Error</title>
</head>
    <body>
        <div style="margin-left: 28%; margin-top: 5%;">
            <div class="error">
                <div class="button-return" onclick="redirecting('${prevPage}')"><</div>
                <p>Ошибка</p>
            </div>
            <div class="info-error">${errorMsg}</div>
        </div>
    </body>
</html>
