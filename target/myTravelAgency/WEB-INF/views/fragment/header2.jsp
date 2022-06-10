<%--
  Created by IntelliJ IDEA.
  User: Anatolii Koliaka
  Date: 07.06.2022
  Time: 14:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>

<style>
    <%@include file="/WEB-INF/styles/header.css" %>
</style>

<head>
    <title></title>
</head>


<header>
    <fmt:setLocale value="${applicationScope.language}"/>

    <ul>
        <li><img src="${pageContext.request.contextPath}/images/travel-icon.png" alt="My Travel logo" class="logo-img"
                 width="108" height="80"></li>
        <li>
            <a class="active" href="${pageContext.request.contextPath}/my-travel?command=home">Home</a>
            <ul>
                <li>
                    <a href="#">Link 1</a>
                </li>
                <li>
                    <a href="#">Link 2</a>
                </li>
                <li>
                    <a href="#">Link 3</a>
                </li>
            </ul>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/my-travel?command=searchTour">Search tour</a>
        </li>
        <!--Account menu-->
        <li>
<%--                <a class="account" href="${pageContext.request.contextPath}/my-travel?command=account">--%>
                    <img src="${pageContext.request.contextPath}/images/account-icon.png" alt="Account" width="40"
                         height="40" class="account">
<%--                </a>--%>
            <ul>
                <li>
                    <a href="#">Link 1</a>
                </li>
                <li>
                    <a href="#">Link 2</a>
                </li>
                <li>
                    <a href="#">Link 3</a>
                </li>
            </ul>

        </li>
    </ul>

</header>
</html>
