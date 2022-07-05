<%--
  Created by IntelliJ IDEA.
  User: Anatolii Koliaka
  Date: 09.06.2022
  Time: 13:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<html>
<%----**************************************************************************----%>
<%----------------locale "uk" seted as default in web.xml file----------------------%>
<%----when session has attribute currentLocale change default locale to requested---%>
<%----**************************************************************************----%>
<c:if test="${not empty sessionScope.currentLocale}">
    <%-- set the locale --%>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <%-- load the bundle (by locale) --%>
    <fmt:setBundle basename="language"/>
</c:if>

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
            <%--Searching window--%>
            <li class="menu-item" id="searchWindow" style="visibility: hidden">
                <label>
                    <input type="text" id="searchInput" placeholder="<fmt:message key="header_jsp.menu.search_input.placeholder"/>">
                </label>
                <button type="button" onclick="searchTourByName()"><fmt:message key="header_jsp.menu.search_input.btn.search"/></button>
            </li>

            <li class="menu-item"><a href="${pageContext.request.contextPath}/my-travel?command=home"><fmt:message
                    key="header_jsp.menu.link.home"/></a></li>
            <li class="menu-item">
                <a class="sub-btn"><fmt:message key="header_jsp.menu.btn.search_tour"/></a>
                <ul class="sub-menu">
                    <li class="sub-item more">
                        <a class="more-btn"><fmt:message key="header_jsp.menu.btn.tour_type"/></a>
                        <ul class="more-menu">
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&type=holidays">
                                <fmt:message key="header_jsp.menu.btn.tour_type.link.holidays"/></a>
                            </li>
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&type=excursion">
                                <fmt:message key="header_jsp.menu.btn.tour_type.link.excursion"/>
                            </a>
                            </li>
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&type=shopping">
                                <fmt:message key="header_jsp.menu.btn.tour_type.link.shopping"/>
                            </a>
                            </li>
                        </ul>
                    </li>
                    <li class="sub-item more">
                        <a class="more-btn"><fmt:message key="header_jsp.menu.btn.tour_price"/></a>
                        <ul class="more-menu">
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&price=desc">
                                <fmt:message key="header_jsp.menu.btn.tour_price.link.desc"/></a></li>
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&price=asc">
                                <fmt:message key="header_jsp.menu.btn.tour_price.link.asc"/></a></li>
                        </ul>
                    </li>
                    <li class="sub-item more">
                        <a class="more-btn"><fmt:message key="header_jsp.menu.btn.person"/></a>
                        <ul class="more-menu">
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&person=one">
                                <fmt:message key="header_jsp.menu.btn.person.link.for_one"/></a></li>
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&person=two">
                                <fmt:message key="header_jsp.menu.btn.person.link.for_two"/></a></li>
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&person=family">
                                <fmt:message key="header_jsp.menu.btn.person.link.for_family"/></a></li>
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&person=group">
                                <fmt:message key="header_jsp.menu.btn.person.link.for_group"/></a></li>
                        </ul>
                    </li>
                    <li class="sub-item more">
                        <a class="more-btn"><fmt:message key="header_jsp.menu.btn.hotel_type"/></a>
                        <ul class="more-menu">
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&hotel=apartment">
                                <fmt:message key="header_jsp.menu.btn.hotel_type.link.apartment"/></a>
                            </li>
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&hotel=hostel">
                                <fmt:message key="header_jsp.menu.btn.hotel_type.link.hostel"/></a>
                            </li>
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&hotel=tourist">
                                <fmt:message key="header_jsp.menu.btn.hotel_type.link.tourist"/></a></li>
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&hotel=comfort">
                                <fmt:message key="header_jsp.menu.btn.hotel_type.link.comfort"/></a></li>
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&hotel=premium">
                                <fmt:message key="header_jsp.menu.btn.hotel_type.link.premium"/></a></li>
                            <li class="more-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=searchTour&hotel=boutique">
                                <fmt:message key="header_jsp.menu.btn.hotel_type.link.boutique"/></a></li>
                        </ul>
                    </li>

                    <li class="sub-item more"><a onclick="showSearchWindow()">
                        <fmt:message key="header_jsp.menu.btn.search_by_name"/></a>
                    </li>
                </ul>
            </li>

            <%--  *** ONLY for users with role "admin" and "manager" *** --%>
            <c:if test="${not empty user}">
                <c:choose>
                    <c:when test="${userRole == 'manager'}">
                        <li class="menu-item"><a class="sub-btn"><fmt:message
                                key="header_jsp.menu.btn.manager_tools"/></a>
                            <ul class="sub-menu">
                                <li class="sub-item"><a
                                        href="${pageContext.request.contextPath}/my-travel?command=tourEditor">
                                    <fmt:message key="header_jsp.menu.btn.manager_tools.link.manage_tours"/></a></li>
                                <li class="sub-item"><a
                                        href="${pageContext.request.contextPath}/my-travel?command=orderEditor">
                                    <fmt:message key="header_jsp.menu.btn.manager_tools.link.manage_orders"/></a></li>
                            </ul>
                        </li>
                    </c:when>
                    <c:when test="${userRole == 'admin'}">
                        <li class="menu-item"><a class="sub-btn"><fmt:message
                                key="header_jsp.menu.btn.admin_tools"/></a>
                            <ul class="sub-menu">
                                <li class="sub-item"><a
                                        href="${pageContext.request.contextPath}/my-travel?command=tourEditor">
                                    <fmt:message key="header_jsp.menu.btn.admin_tools.link.tour_editor"/></a></li>
                                <li class="sub-item"><a
                                        href="${pageContext.request.contextPath}/my-travel?command=orderEditor">
                                    <fmt:message key="header_jsp.menu.btn.admin_tools.link.order_editor"/></a></li>
                                <li class="sub-item"><a
                                        href="${pageContext.request.contextPath}/my-travel?command=userEditor&role=user">
                                    <fmt:message key="header_jsp.menu.btn.admin_tools.link.user_editor"/></a></li>
                                <li class="sub-item"><a
                                        href="${pageContext.request.contextPath}/my-travel?command=userEditor&role=manager">
                                    <fmt:message key="header_jsp.menu.btn.admin_tools.link.manager_editor"/></a></li>
                            </ul>
                        </li>
                    </c:when>
                </c:choose>
            </c:if>

            <li class="menu-item">
                <a class="sub-btn"><fmt:message key="header_jsp.menu.btn.language"/></a>
                <img src="${pageContext.request.contextPath}/images/world-icon.png" width="18px" height="18px">
                <ul class="sub-menu">
                    <li class="sub-item"><a href="?currentLocale=en"><fmt:message
                            key="header_jsp.menu.btn.language.link.english"/></a></li>
                    <li class="sub-item"><a href="?currentLocale=uk"><fmt:message
                            key="header_jsp.menu.btn.language.link.ukrainian"/></a></li>
                </ul>
            </li>
            <li class="menu-item"><img src="${pageContext.request.contextPath}/images/account-icon.png" alt="Account"
                                       width="40"
                                       height="40" class="account-btn">
                <ul class="sub-menu">
                    <c:choose>
                        <c:when test="${not empty user}">
                            <c:if test="${userRole == 'user'}">
                                <li class="sub-item more">
                                    <a class="more-btn"><fmt:message
                                            key="header_jsp.menu.btn.account.btn.my_cabinet"/></a>
                                    <ul class="more-menu">
                                        <li class="more-item"><a
                                                href="${pageContext.request.contextPath}/my-travel?command=userCabinet&menu=myOrders">
                                            <fmt:message
                                                    key="header_jsp.menu.btn.account.btn.my_cabinet.link.my_orders"/></a>
                                        </li>
                                        <li class="more-item"><a
                                                href="${pageContext.request.contextPath}/my-travel?command=userCabinet&menu=myInfo">
                                            <fmt:message
                                                    key="header_jsp.menu.btn.account.btn.my_cabinet.link.my_info"/></a>
                                        </li>
                                    </ul>
                                </li>
                            </c:if>
                            <c:if test="${userRole == 'manager'}">
                                <li class="sub-item"><a href="${pageContext.request.contextPath}/my-travel?command=userCabinet&menu=myInfo">
                                <fmt:message key="header_jsp.menu.btn.account.btn.my_cabinet.link.my_profile"/></a>
                            </c:if>
                            <li class="sub-item"><a href="${pageContext.request.contextPath}/my-travel?command=logout">
                                <fmt:message key="header_jsp.menu.btn.account.btn.my_cabinet.link.logout"/></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="sub-item"><a
                                    href="${pageContext.request.contextPath}/my-travel?command=loginPage">
                                <fmt:message key="header_jsp.menu.btn.account.link.login_register"/></a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </li>
        </ul>
    </div>

    <script>
        let searchWindow = document.getElementById("searchWindow")

        function showSearchWindow() {
            searchWindow.style.visibility = "visible"
        }

        function searchTourByName() {
            let input = document.getElementById("searchInput").value;

            fetch("${pageContext.request.contextPath}/my-travel?command=searchTour&name=" + input, {method: "GET"})
                .then(response => {
                    window.location.href = "${pageContext.request.contextPath}/my-travel?command=searchTour&name=" + input
                });
        }
    </script>

</header>

</body>
</html>
