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
    <%@include file="/WEB-INF/styles/header.css" %>
</style>

<head>
    <title>
        ${title}
    </title>
</head>

<body>

<header>
    <%--    Set user attributes--%>
    <c:set var="user" value="${user}" scope="session"/>
    <c:set var="userRole" value="${userRole}" scope="session"/>

    <img src="${pageContext.request.contextPath}/images/travel-icon.png" alt="Logo" class="logo-img" width="108"
         height="80">
    <p class="logo">My travel</p>
    <div class="navigation">
        <ul class="menu">
            <li class="menu-item"><a href="${pageContext.request.contextPath}/my-travel?command=home">Home</a></li>
            <li class="menu-item">
                <a class="sub-btn">Search tour</a>
                <ul class="sub-menu">
                    <li class="sub-item more">
                        <a class="more-btn">Tour type</a>
                        <ul class="more-menu">
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&type=holidays">Holidays</a>
                            </li>
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&type=excursion">Excursion</a>
                            </li>
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&type=shopping">Shopping</a>
                            </li>
                        </ul>
                    </li>
                    <li class="sub-item more">
                        <a class="more-btn">Tour price</a>
                        <ul class="more-menu">
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&price=desc">Expensive
                                first</a></li>
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&price=asc">Cheaper
                                first</a></li>
                        </ul>
                    </li>
                    <li class="sub-item more">
                        <a class="more-btn">Person</a>
                        <ul class="more-menu">
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&person=one">For
                                one</a></li>
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&person=two">For
                                two</a></li>
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&person=family">For family
                                (3-5)</a></li>
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&person=group">For group
                                (6< )</a></li>
                        </ul>
                    </li>
                    <li class="sub-item more">
                        <a class="more-btn">Hotel type</a>
                        <ul class="more-menu">
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&hotel=apartment">Apartment</a>
                            </li>
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&hotel=hostel">Hostel</a>
                            </li>
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&hotel=tourist">Tourist
                                hotel</a></li>
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&hotel=comfort">Comfort
                                hotel</a></li>
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&hotel=premium">Premium
                                hotel</a></li>
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&hotel=boutique">Boutique
                                hotel</a></li>
                        </ul>
                    </li>
                </ul>
            </li>

            <%--  *** ONLY for users with role "admin" and "manager" *** --%>
            <c:if test="${not empty user}">
                <c:choose>
                    <c:when test="${userRole == 'manager'}">
                        <li class="menu-item"><a class="sub-btn">Manager tools</a>
                            <ul class="sub-menu">
                                <li class="sub-item"><a
                                        href="${pageContext.request.contextPath}/my-travel?command=tourEditor">Manage
                                    tours</a></li>
                                <li class="sub-item"><a
                                        href="${pageContext.request.contextPath}/my-travel?command=orderEditor">Manage
                                    orders</a></li>
                            </ul>
                        </li>
                    </c:when>
                    <c:when test="${userRole == 'admin'}">
                        <li class="menu-item"><a class="sub-btn">Admin tools</a>
                            <ul class="sub-menu">
                                <li class="sub-item"><a
                                        href="${pageContext.request.contextPath}/my-travel?command=tourEditor">Tour
                                    editor</a></li>
                                <li class="sub-item"><a
                                        href="${pageContext.request.contextPath}/my-travel?command=orderEditor">Order
                                    editor</a></li>
                                <li class="sub-item"><a
                                        href="${pageContext.request.contextPath}/my-travel?command=userEditor">User
                                    editor</a></li>
                                <li class="sub-item"><a
                                        href="${pageContext.request.contextPath}/my-travel?command=userEditor">Manager
                                    editor</a></li>
                            </ul>
                        </li>
                    </c:when>
                </c:choose>
            </c:if>

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
                    <c:choose>
                        <c:when test="${not empty user}">
                            <c:if test="${userRole == 'user'}">
<%--                                <li class="sub-item"><a href="${pageContext.request.contextPath}/my-travel?command=userCabinet">My cabinet</a>--%>

                            <li class="sub-item more">
                                <a class="more-btn">My Cabinet</a>
                                <ul class="more-menu">
                                    <li class="more-item"><a
                                            href="${pageContext.request.contextPath}/my-travel?command=userCabinet&menu=myOrders">My orders</a>
                                    </li>
                                    <li class="more-item"><a
                                            href="${pageContext.request.contextPath}/my-travel?command=userCabinet&menu=myInfo">My info</a>
                                    </li>
                                </ul>
                            </li>
                            </c:if>
                            <c:if test="${userRole == 'manager'}">
                                <li class="sub-item"><a href="${pageContext.request.contextPath}/my-travel?command=managerProfile">My profile</a>
                            </c:if>
                            <li class="sub-item"><a href="${pageContext.request.contextPath}/my-travel?command=logout">Logout</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="sub-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=loginPage">Login/Register</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </li>
        </ul>
    </div>

</header>

</body>
</html>
