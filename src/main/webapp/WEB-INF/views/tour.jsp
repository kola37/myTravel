<%--
  Created by IntelliJ IDEA.
  User: Anatolii Koliaka
  Date: 17.06.2022
  Time: 13:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="entity.constant.TourType" %>

<html>
<c:set var="title" value="Tour | My Travel Agency"/>
<c:set var="user" value="${user}"/>
<c:set var="tour" value="${tour}"/>

<%@ include file="/WEB-INF/views/fragment/header.jsp" %>

<style>
    <%@ include file="/WEB-INF/styles/order.css" %>
</style>

<body>


<div class="tour-order-box" id="tourForm">
    <img src="<c:out value="${pageContext.request.contextPath}${tour.image}"/>" alt="image"/>
    <div class="tour-order-content">
        <h3>${tour.name}</h3>
        <c:if test="${tour.isHot() == 'true'}">
            <h2>Hot tour!</h2>
        </c:if>
        <p>Type: ${TourType.getType(tour.tourTypeId).getName()}</p>
        <p>Hotel: ${hotels.stream().filter(hotel -> hotel.getId()==tour.hotelId).toList().get(0).name}</p>
        <p>Person: ${tour.numOfPersons}</p>
        <p>Price: ${tour.price} $</p>
        <p>Overview: ${tour.description}</p>
        <c:if test="${userRole == 'user'}">
            <form method="get" action="${pageContext.request.contextPath}/my-travel?command=orderPage">
                <input type="hidden" name="command" value="orderPage">
                <input type="hidden" name="tourId" value="${tour.id}">
                <button type="submit" class="tour-order-btn" id="showOrderForm">Make an order</button>
            </form>
        </c:if>
        <c:if test="${user == null}">
            <button class="tour-order-btn" id="showAlert">Make an order</button>
        </c:if>
    </div>
</div>

<%--Allerrt message for non-registered users!--%>
<div class="tour-order-box" id="alertMsg">
    <span class="closeBtn" id="closeAlert">×</span>
    <h3>Registered users only can make an order!</h3>
    <h3>Please, sign up to continue!</h3>
    <li class="tour-order-btn">
        <c:set var="commandPrevious" value="/my-travel?command=tourPage&tourId=${tour.id}" scope="session"/>
        <a href="${pageContext.request.contextPath}/my-travel?command=loginPage">Login/Register</a>
    </li>
</div>

<%--Show alert message--%>
<script>
    let alertMsg = document.getElementById("alertMsg")
    let showAlert = document.getElementById("showAlert")
    let tourForm = document.getElementById("tourForm")
    showAlert.addEventListener("click", () => {
        alertMsg.style.display = "block"
        tourForm.style.display = "none"
    })
</script>

<%--Close alert message--%>
<script>
    let closeAlert = document.getElementById("closeAlert")
    closeAlert.addEventListener("click", () => {
        alertMsg.style.display = "none"
        tourForm.style.display = "block"
    })
</script>

</body>
</html>
