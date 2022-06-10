<%--
  Created by IntelliJ IDEA.
  User: Anatolii Koliaka
  Date: 09.06.2022
  Time: 13:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<style>
    <%@include file="/WEB-INF/styles/header3.css" %>
</style>


<body>

<header>

    <img src="${pageContext.request.contextPath}/images/travel-icon.png" alt="Logo" class="logo-img" width="108"
         height="80">
    <a href="#" class="logo">My travel</a>
    <div class="navigation">
        <ul class="menu">
            <li class="menu-item"><a href="${pageContext.request.contextPath}/my-travel?command=home">Home</a></li>
            <li class="menu-item">
                <a class="sub-btn" href="${pageContext.request.contextPath}/my-travel?command=searchTour">Search
                    tour</a>
                <ul class="sub-menu">
                    <li class="sub-item"><a href="#">Item 1</a></li>
                    <li class="sub-item"><a href="#">Item 2</a></li>
                    <li class="sub-item"><a href="#">Item 3</a></li>
                </ul>
            </li>
            <li class="menu-item"><a href="${pageContext.request.contextPath}/my-travel?command=manageTour">Manage
                tour</a></li>
            <li class="menu-item">
                <a href="#">Language</a>
                <img src="${pageContext.request.contextPath}/images/world-icon.png" width="18px" height="18px">
                <ul class="sub-menu">
                    <li class="sub-item"><a href="#">english</a></li>
                    <li class="sub-item"><a href="#">українська</a></li>
                </ul>
            </li>
            <li class="menu-item"><img src="${pageContext.request.contextPath}/images/account-icon.png" alt="Account"
                                       width="40"
                                       height="40" class="account-btn">
                <ul class="sub-menu">
                    <c:set var="user" value="${user}" scope="session"/>
                    <c:choose>
                        <c:when test="${not empty user}">
                            <li class="sub-item"><a href="${pageContext.request.contextPath}/my-travel?command=logout">Logout</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="sub-item"><a href="${pageContext.request.contextPath}/my-travel?command=login">Login</a></li>
                        </c:otherwise>
                    </c:choose>
                    <li class="sub-item"><a href="#">Item item2</a></li>
                    <li class="sub-item"><a href="#">Item 3</a></li>
                </ul>
            </li>

        </ul>
    </div>

</header>

</body>
</html>
