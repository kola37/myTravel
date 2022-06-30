<%--
  Created by IntelliJ IDEA.
  User: anatolii
  Date: 17.06.2022
  Time: 17:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="entity.constant.OrderStatus" %>
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
        <h3><fmt:message key="home_jsp.greeting.user.hello"/> ${userLogin}!</h3>
        <hr>
    </div>
</c:if>

<%--Show user orders when MyOrders menu chosen--%>
<c:if test="${menu == 'myOrders'}">
    <div class="tittle-container">
        <div class="tittle-item">
            <h2><fmt:message key="user_cabinet_jsp.orders.container.tittle"/></h2>
        </div>
    </div>

    <div class="container">
        <div class="table">

            <div class="table-header">
                <div class="header-item"><fmt:message key="user_cabinet_jsp.orders.container.column.id"/></div>
                <div class="header-item"><fmt:message key="user_cabinet_jsp.orders.container.column.date"/></div>
                <div class="header-item"><fmt:message key="user_cabinet_jsp.orders.container.column.tour"/></div>
                <div class="header-item"><fmt:message key="user_cabinet_jsp.orders.container.column.hotel"/></div>
                <div class="header-item"><fmt:message key="user_cabinet_jsp.orders.container.column.person"/></div>
                <div class="header-item"><fmt:message key="user_cabinet_jsp.orders.container.column.discount"/></div>
                <div class="header-item"><fmt:message key="user_cabinet_jsp.orders.container.column.total"/></div>
                <div class="header-item"><fmt:message key="user_cabinet_jsp.orders.container.column.status"/></div>
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
            <h2><fmt:message key="user_cabinet_jsp.my_info.form.tittle"/></h2>
        </div>
        <div id="readable" class="input-group">
            <div>
                <p><fmt:message key="user_cabinet_jsp.my_info.form.field.login"/></p>
                <h3>${user.login}</h3>
                <hr>
            </div>
            <div>
                <p><fmt:message key="user_cabinet_jsp.my_info.form.field.first_name"/></p>
                <h3>${user.firstName}</h3>
                <hr>
            </div>
            <div>
                <p><fmt:message key="user_cabinet_jsp.my_info.form.field.last_name"/></p>
                <h3>${user.lastName}</h3>
                <hr>
            </div>
            <div>
                <p><fmt:message key="user_cabinet_jsp.my_info.form.field.email"/></p>
                <h3>${user.email}</h3>
                <hr>
            </div>
            <button type="button" class="submit-btn" id="showEditable"><fmt:message key="user_cabinet_jsp.my_info.form.btn.edit"/></button>
        </div>

        <form id="editable" class="input-group" action="${pageContext.request.contextPath}/my-travel?command=editUser"
              method="post">
            <label>
                <p><fmt:message key="user_cabinet_jsp.my_info.form.field.login"/></p>
                <input class="input-field" type="text" placeholder="<fmt:message key="login_register_jsp.form.placeholder.login"/>" name="login" value="${user.login}" required
                       maxlength="15">
            </label>
            <label>
                <p><fmt:message key="user_cabinet_jsp.my_info.form.field.password"/></p>
                <input class="input-field" type="password" placeholder="<fmt:message key="login_register_jsp.form.placeholder.password"/>" name="password"
                       value="${user.password}" required maxlength="15">
            </label>
            <label>
                <p><fmt:message key="user_cabinet_jsp.my_info.form.field.first_name"/></p>
                <input class="input-field" type="text" placeholder="<fmt:message key="login_register_jsp.form.placeholder.first_name"/>" name="firstName"
                       value="${user.firstName}" required
                       maxlength="20">
            </label>
            <label>
                <p><fmt:message key="user_cabinet_jsp.my_info.form.field.last_name"/></p>
                <input class="input-field" type="text" placeholder="<fmt:message key="login_register_jsp.form.placeholder.last_name"/>" name="lastName"
                       value="${user.lastName}" required maxlength="20">
            </label>
            <label>
                <p><fmt:message key="user_cabinet_jsp.my_info.form.field.email"/></p>
                <input class="input-field" type="email" placeholder="<fmt:message key="login_register_jsp.form.placeholder.email"/>" name="email"
                       value="${user.email}" required maxlength="40">
            </label>
            <label>
                <input class="input-field" type="hidden" name="role" value="${userRole}" required>
            </label>
            <button type="submit" class="submit-btn"><fmt:message key="user_cabinet_jsp.my_info.form.btn.save"/></button>
        </form>
    </div>
</c:if>

<%@ include file="/WEB-INF/views/fragment/footer.jsp" %>

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
