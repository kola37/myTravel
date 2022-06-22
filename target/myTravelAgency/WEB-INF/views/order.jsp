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
<c:set var="title" value="Order | My Travel Agency"/>
<c:set var="user" value="${user}"/>
<c:set var="tour" value="${tour}"/>
<c:set var="discount" value="${discount}"/>
<c:set var="totalPrice" value="${totalPrice}"/>
<c:set var="message" value="${message}"/>


<%@ include file="/WEB-INF/views/fragment/header.jsp" %>

<%------------------------------------------------------------------------------%>
<%--Users only can make an order!!! --%>
<%------------------------------------------------------------------------------%>

<style>
    <%@ include file="/WEB-INF/styles/order.css" %>
</style>

<body>

<form id="orderForm" action="${pageContext.request.contextPath}/my-travel?command=confirmOrder"
      method="post">
    <div class="tour-order-box">
        <h3>This is an order confirmation form:</h3>
        <hr>
        <input type="hidden" value="${tour.id}" name="tourId">
        <h2>Tour: ${tour.name}</h2>
        <h2>Client: ${user.firstName} ${user.lastName}</h2>
        <p>Type: ${TourType.getType(tour.tourTypeId).getName()}</p>
        <p>Hotel: ${hotels.stream().filter(hotel -> hotel.getId()==tour.hotelId).toList().get(0).name}</p>
        <p>Person: ${tour.numOfPersons}</p>
        <c:if test="${discount != '0'}">
            <p>Price: ${tour.price} $</p>
            <p>Your discount: ${discount} %</p>
            <input type="hidden" name="discount" value="${discount}">
        </c:if>
        <p>Total: ${totalPrice} $</p>
        <input type="hidden" name="totalPrice" value="${totalPrice}">
        <button type="submit" class="tour-order-btn">Confirm</button>
    </div>
</form>

</body>
</html>
