<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles.css"/><%--
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>--%>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/jquery.maskedinput.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts.js"></script>

<html>
  <head>
    <title>Главная</title>
  </head>

  <body onload="stopAnimation()">
    <div class="loader-wrapper">
      <div class="loader"></div>
      <div class="loader-section section-left"></div>
      <div class="loader-section section-right"></div>
    </div>

    <div style="margin-left: 36%; margin-top: 15%;">
      <div class="site-title">Фильмография</div>
      <div class="btn-circle" onclick="redirecting('/films')">Фильмы<span></span></div>
      <div class="btn-circle" onclick="redirecting('/franchises')">Франшизы<span></span></div>
      <div class="btn-circle" onclick="redirecting('/producers')">Режиссеры<span></span></div>
      <div class="btn-circle" onclick="redirecting('/genres')">Жанры<span></span></div>
    </div>
  </body>
</html>
