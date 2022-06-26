<%--
  Created by IntelliJ IDEA.
  User: Anatolii Koliaka
  Date: 23.06.2022
  Time: 17:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="entity.constant.UserRole" %>
<%@ page import="entity.constant.OrderStatus" %>


<html>
<c:set var="users" value="${users}"/>
<c:set var="orders" value="${orders}"/>

<%@ include file="/WEB-INF/views/fragment/header.jsp" %>
<style>
    <%@ include file="/WEB-INF/styles/userCabinet.css" %>
</style>
<body>

<%--Message for admin amd manager--%>
<c:if test="${userRole == 'admin' || userRole == 'manager'}">
<div class="info-msg">
    <h3>Hello, ${userLogin}! This is ${userRole} version of site!</h3>
    <hr>
</div>
</c:if>

<div class="tittle-container">
    <div class="tittle-item">
        <h2>Users</h2>
        <c:if test="${userRole == 'admin'}">
            <h2><a href="${pageContext.request.contextPath}/my-travel?command=addManager">Add new</a></h2>
        </c:if>
    </div>
</div>

<div class="container">
    <div class="table">

        <div class="table-header">
            <div class="header-item">id</div>
            <div class="header-item">Login</div>
            <div class="header-item">First name</div>
            <div class="header-item">Last name</div>
            <div class="header-item">Email</div>
            <div class="header-item">Orders</div>
            <div class="header-item">Paid orders</div>
            <div class="header-item">Cancelled orders</div>
            <div class="header-item">Role</div>
            <div class="header-item">Blocked</div>
            <div class="header-item">Edit</div>
        </div>
        <div class="table-content">
            <c:forEach var="user" items="${users}">
                <div class="table-row">
                    <div class="table-data">${user.id}</div>
                    <div class="table-data">${user.login}</div>
                    <div class="table-data">${user.firstName}</div>
                    <div class="table-data">${user.lastName}</div>
                    <div class="table-data">${user.email}</div>
                    <div class="table-data">${orders.stream().filter(order -> order.userId==user.getId()).toList().size()}</div>
                    <div class="table-data">${orders.stream().filter(order -> order.userId==user.getId()&&order.statusId==OrderStatus.PAID.getIndex()).toList().size()}</div>
                    <div class="table-data">${orders.stream().filter(order -> order.userId==user.getId()&&order.statusId==OrderStatus.CANCELED.getIndex()).toList().size()}</div>
                    <div class="table-data">${UserRole.getRole(user.roleId)}</div>
                    <div class="table-data">${user.isBlocked()? 'yes' : 'no'}</div>

                    <div class="table-data" id="${user.id}">
                        <img id="editBtn" src="${pageContext.request.contextPath}/images/edit-icon.png"
                             alt="edit" width="20px" height="20px"/>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>


<body>

</body>
</html>
