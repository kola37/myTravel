<%--
  Created by IntelliJ IDEA.
  User: Anatolii Koliaka
  Date: 07.06.2022
  Time: 15:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="entity.constant.TourType" %>

<html>
<c:set var="title" value="Home | My Travel Agency"/>
<c:set var="tours" value="${tours}"/>
<c:set var="hotels" value="${hotels}"/>
<c:set var="user" value="${user}" scope="session"/>
<c:set var="userRole" value="${userRole}" scope="session"/>
<c:set var="userLogin" value="${userLogin}" scope="session"/>
<c:set var="message" value="${message}" scope="session"/>
<c:set var="searchParamMessage" value="${paramMessage}"/>


<%@ include file="/WEB-INF/views/fragment/header.jsp" %>
<style>
    <%@ include file="/WEB-INF/styles/style.css" %>
</style>

<body>

<%--Greeting for users--%>
<c:if test="${userRole == 'user'}">
    <div class="info-msg">
        <h3>Hello, ${userLogin}!</h3>
        <hr>
    </div>
</c:if>

<%--Message for admin amd manager--%>
<c:if test="${userRole == 'admin' || userRole == 'manager'}">
    <div class="info-msg">
        <h3>Hello, ${userLogin}! This is ${userRole} version of site!</h3>
        <hr>
    </div>
</c:if>

<%--Searching parameters--%>
<c:if test="${not empty searchParamMessage}">
    <div class="info-msg" id="search-param">
        <h3>Searching parameter: ${searchParamMessage}, found ${tours.size()} tour(s)
            <a class="closeBtn" id="search-param-close"
           href="${pageContext.request.contextPath}/my-travel?command=home">×</a></h3>
        <hr>
    </div>
</c:if>

<%--Information message after order confirmed or user edited--%>
<c:if test="${not empty message}">
    <div class="tour-order-box" id="alertMsg">
        <span class="closeBtn" id="closeAlert">×</span>
        <h2>${message}</h2>
        <c:remove var="message"/>
    </div>
</c:if>

<%--Create tour card--%>
<c:forEach var="tour" items="${tours}">
    <div class="tour-container">
        <div class="tour-wrapper">

            <div class="tour-block">
                <h1><c:out value="${tour.name}"/></h1>
                <img src="<c:out value="${pageContext.request.contextPath}${tour.image}"/>" alt="image"/>

                <c:if test="${tour.isHot()}">
                    <div class="hot-image">
                        <img id="hot-img" src="${pageContext.request.contextPath}/images/hot-tour-icon.png" alt="hot">
                    </div>
                </c:if>

                <div class="info-block">
                    <h3>More Info</h3>
                    <p>Type: ${TourType.getType(tour.tourTypeId).getName()}</p>
                    <p>Hotel: ${hotels.stream().filter(hotel -> hotel.getId()==tour.hotelId).toList().get(0).name}</p>
                    <p>Person: ${tour.numOfPersons}</p>
                    <p>Price: ${tour.price} $</p>
                    <li class="more-info-btn">
                        <a href="${pageContext.request.contextPath}/my-travel?command=tourPage&tourId=${tour.id}">More info</a>
                    </li>
                </div>
            </div>
        </div>
    </div>
</c:forEach>

<script>
    let alertMsg = document.getElementById("alertMsg")
    let closeAlert = document.getElementById("closeAlert")

    closeAlert.addEventListener("click", () => {
        alertMsg.style.display = "none"
    })
</script>

</body>
</html>
