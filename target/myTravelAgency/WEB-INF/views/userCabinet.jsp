<%--
  Created by IntelliJ IDEA.
  User: anatolii
  Date: 17.06.2022
  Time: 17:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="entity.constant.OrderStatus" %>
<%@ page import="entity.constant.UserRole" %>
<%--<%@ taglib prefix="tet" uri="my-travel/tourEntityTag" %>--%>

<html>
<c:set var="title" value="My cabinet | My Travel Agency"/>
<c:set var="user" value="${user}" scope="session"/>
<c:set var="userRole" value="${userRole}" scope="session"/>
<c:set var="userLogin" value="${userLogin}" scope="session"/>
<c:set var="orders" value="${userOrders}"/>
<c:set var="menu" value="${menu}" scope="request"/>

<%@ include file="/WEB-INF/views/fragment/header.jsp" %>
<style>
    <%@ include file="/WEB-INF/styles/userCabinet.css" %>
</style>
<body>

<%--Greeting for users and managers--%>
<c:if test="${userRole == 'user' || userRole == 'manager'}">
    <div class="info-msg">
        <h3>Hello, ${userLogin}!</h3>
        <hr>
    </div>
</c:if>

<%--Show user orders when MyOrders menu chosen--%>
<c:if test="${menu == 'myOrders'}">
    <div class="tittle-container">
        <div class="tittle-item">
            <h2>My orders</h2>
        </div>
    </div>

    <div class="container">
        <div class="table">

            <div class="table-header">
                <div class="header-item">№</div>
                <div class="header-item">Date</div>
                <div class="header-item">Tour</div>
                <div class="header-item">Hotel</div>
                <div class="header-item">Person</div>
                <div class="header-item">Discount</div>
                <div class="header-item">Total</div>
                <div class="header-item">Status</div>
            </div>
            <div class="table-content">
                <c:forEach var="order" items="${orders}">
                    <div class="table-row">
                        <div class="table-data">${orders.indexOf(order) + 1}</div>
                        <div class="table-data">${order.orderDate}</div>
                        <div class="table-data">${tours.stream().filter(tour -> tour.getId()==order.tourId).toList().get(0).name}</div>
                        <div class="table-data">${hotels.stream().filter(hotel -> hotel.getId()==tours.stream().filter(tour -> tour.getId()==order.tourId).toList().get(0).hotelId).toList().get(0).name}</div>
                        <div class="table-data">${tours.stream().filter(tour -> tour.getId()==order.tourId).toList().get(0).numOfPersons}</div>
                        <div class="table-data">${order.discount}</div>
                        <div class="table-data">${order.totalPrice}</div>
                        <div class="table-data">${OrderStatus.getStatus(order.statusId)}</div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</c:if>

<%--Show user information when MyInfo menu chosen--%>
<c:if test="${menu == 'myInfo'}">
    <div class="form-box">
        <div>
            <h2>My info:</h2>
        </div>
        <div id="readable" class="input-group">
            <div>
                <p>Login:</p>
                <h3>${user.login}</h3>
                <hr>
            </div>
            <div>
                <p>First name:</p>
                <h3>${user.firstName}</h3>
                <hr>
            </div>
            <div>
                <p>Last name:</p>
                <h3>${user.lastName}</h3>
                <hr>
            </div>
            <div>
                <p>Email:</p>
                <h3>${user.email}</h3>
                <hr>
            </div>
            <button type="button" class="submit-btn" id="showEditable">Edit</button>
        </div>

        <form id="editable" class="input-group" action="${pageContext.request.contextPath}/my-travel?command=editUser" method="post">
            <label>
                <p>Login:</p>
                <input class="input-field" type="text" placeholder="login" name="login" value="${user.login}" required maxlength="15">
            </label>
            <label>
                <p>Password:</p>
                <input class="input-field" type="password" placeholder="password" name="password"
                       value="${user.password}" required maxlength="15">
            </label>
            <label>
                <p>First name:</p>
                <input class="input-field" type="text" placeholder="first name" name="firstName"
                       value="${user.firstName}" required
                       maxlength="20">
            </label>
            <label>
                <p>Last name:</p>
                <input class="input-field" type="text" placeholder="last name" name="lastName"
                       value="${user.lastName}" required maxlength="20">
            </label>
            <label>
                <p>Email:</p>
                <input class="input-field" type="email" placeholder="email" name="email"
                       value="${user.email}" required maxlength="40">
            </label>
            <label>
                <input class="input-field" type="hidden" name="role" value="${userRole}" required>
            </label>
            <button type="submit" class="submit-btn">Save</button>
        </form>
    </div>
</c:if>

<%--Show editable form--%>
<script>
    let editableForm = document.getElementById("editable")
    let showEditable = document.getElementById("showEditable")
    let readable = document.getElementById("readable")
    showEditable.addEventListener("click", () => {
        editableForm.style.display = "block"
        readable.style.display = "none"
    })
</script>

</body>
</html>
