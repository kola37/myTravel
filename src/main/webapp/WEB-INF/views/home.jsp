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
<c:set var="userRole" value="${userRole}" scope="session"/>
<c:set var="userLogin" value="${userLogin}" scope="session"/>


<%@ include file="/WEB-INF/views/fragment/header.jsp" %>
<style>
    <%@ include file="/WEB-INF/styles/style.css" %>
</style>

<body>

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


<%--<c:forEach var="tour" items="${tours}">--%>
<%--    <tr>--%>
<%--        <td>${tour.name}</td>--%>
<%--        <td>${tour.description}</td>--%>
<%--        <td>${tour.price}</td>--%>
<%--    </tr>--%>
<%--</c:forEach>--%>

<%--Create tour card--%>
<c:forEach var="tour" items="${tours}">


    <div class="tour-container">
        <div class="tour-wrapper">


                <%--            <div class="tour-item">--%>
                <%--                <div class="tour-photo">--%>
                <%--                    <img src="${pageContext.request.contextPath}/images/bora-bora.png" alt="image">--%>
                <%--                </div>--%>
                <%--                    &lt;%&ndash;                    <p><c:out value="${tour.description}"/></p>&ndash;%&gt;--%>
                <%--                    &lt;%&ndash;                    <p><c:out value="${tour.price}"/></p>&ndash;%&gt;--%>
                <%--                    &lt;%&ndash;                    <div class="tour-btn-wrapper"> </div>&ndash;%&gt;--%>
                <%--                    &lt;%&ndash;                    <button class="tour-btn">See details</button>&ndash;%&gt;--%>
                <%--                    &lt;%&ndash;                    <button class="tour-btn">Order tour</button>&ndash;%&gt;--%>
                <%--            </div>--%>


            <figure class="image-block">
                <h1><c:out value="${tour.name}"/></h1>
                <img src="<c:out value="${pageContext.request.contextPath}${tour.image}"/>" alt="image"/>

                <c:if test="${tour.isHot()}">
                    <div class="hot-image">
                        <img id="hot-img" src="${pageContext.request.contextPath}/images/hot-tour-icon.png" alt="hot">
                    </div>
                </c:if>

                <figcaption>
                    <h3>More Info</h3>
                    <p>Type: ${TourType.getType(tour.tourTypeId).getName()}</p>
                    <p>Hotel: ${hotels.stream().filter(hotel -> hotel.getId()==tour.hotelId).toList().get(0).name}</p>
                    <p>Person: ${tour.numOfPersons}</p>
                    <p>Price: ${tour.price} $</p>
                        <%--                    <input type="hidden" name="id" value="${tour.id}"/>--%>
                    <li class="more-info-btn">
                        <a href="${pageContext.request.contextPath}/my-travel?command=orderPage&tourId=${tour.id}">More
                            info</a>
                    </li>
                </figcaption>
            </figure>


        </div>


    </div>

</c:forEach>


<%--<div class="alert">--%>
<%--    <span class="closebtn" onclick="this.parentElement.style.display='none';">×</span>--%>
<%--    <strong>Danger!</strong> Indicates a dangerous or potentially negative action.--%>
<%--</div>--%>


</body>
</html>
