<%--
  Created by IntelliJ IDEA.
  User: Anatolii Koliaka
  Date: 15.06.2022
  Time: 18:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="entity.constant.TourType" %>

<html>
<c:set var="title" value="Tour | Order | My Travel Agency"/>
<c:set var="id" value="${id}"/>
<c:set var="user" value="${user}"/>
<c:set var="tour" value="${tour}"/>
<c:set var="discount" value="${discount}"/>
<c:set var="totalPrice" value="${totalPrice}"/>


<%@ include file="/WEB-INF/views/fragment/header.jsp" %>

<%------------------------------------------------------------------------------%>
<%--Бронировать тур могут только зарегистрированные user!!! --%>
<%------------------------------------------------------------------------------%>

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
        <%--            <a id="showOrderForm" href="" class="button">В корзину</a>--%>
        <c:if test="${userRole == 'user'}">
            <button class="tour-order-btn" id="showOrderForm">Make an order</button>
        </c:if>
        <c:if test="${user == null || userRole != 'user'}">
            <button class="tour-order-btn" id="showAlert">Make an order</button>
        </c:if>
    </div>
</div>

<form id="orderForm" action="${pageContext.request.contextPath}/my-travel?command=createOrder"
      method="post">
    <div class="tour-order-box">
        <input type="hidden" value="${tour.name}" name="tourName">
        <h3>${tour.name}</h3>
        <h2>Client: ${user.firstName} ${user.lastName}</h2>
        <p>Hotel: ${hotels.stream().filter(hotel -> hotel.getId()==tour.hotelId).toList().get(0).name}</p>
        <p>Person: ${tour.numOfPersons}</p>
        <p>Price: ${tour.price} $</p>
        <c:if test="${discount != '0'}">
            <p>Your discount: ${discount} %</p>
        </c:if>
        <p>Total: ${totalPrice} $</p>
        <button type="submit" class="tour-order-btn">Confirm</button>
    </div>
</form>

<%--Allerrt message for non-registered users!--%>
<div class="tour-order-box" id="alertMsg">
    <span class="closeBtn" id="closeAlert">×</span>
    <h3>Registered users only can make an order!</h3>
    <h3>Please, sign up to continue!</h3>
    <li class="tour-order-btn">
        <c:set var="commandPrevious" value="/my-travel?command=orderPage&tourId=${tour.id}" scope="session"/>
        <a href="${pageContext.request.contextPath}/my-travel?command=loginPage">Login/Register</a>
    </li>
</div>


<%--Show order form--%>
<script>
    let orderForm = document.getElementById("orderForm")
    let tourForm = document.getElementById("tourForm")
    let showOrderForm = document.getElementById("showOrderForm")

    showOrderForm.addEventListener("click", () => {
        orderForm.style.display = "block"
        tourForm.style.display = "none"
    })
</script>

<%--Show alert message--%>
<script>
    let alertMsg = document.getElementById("alertMsg")
    let showAlert = document.getElementById("showAlert")

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
