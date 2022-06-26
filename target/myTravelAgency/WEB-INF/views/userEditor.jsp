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
<c:set var="title" value="${tableTittle} editor | My Travel Agency"/>
<c:set var="userRole" value="${userRole}" scope="session"/>
<c:set var="userLogin" value="${userLogin}" scope="session"/>
<c:set var="users" value="${users}" scope="request"/>
<c:set var="orders" value="${orders}" scope="request"/>
<c:set var="tableTittle" value="${tableTittle}" scope="request"/>

<%@ include file="/WEB-INF/views/fragment/header.jsp" %>
<style>
    <%@ include file="/WEB-INF/styles/userCabinet.css" %>
</style>

<body>

<%--Message for admin--%>
<c:if test="${userRole == 'admin'}">
    <div class="info-msg">
        <h3>Hello, ${userLogin}! This is ${userRole} version of site!</h3>
        <hr>
    </div>
</c:if>

<div class="tittle-container">
    <div class="tittle-item">
        <h2 id="roleName">${tableTittle}s</h2>
        <c:if test="${tableTittle == 'Managers'}">
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
            <c:if test="${tableTittle == 'Users'}">
                <div class="header-item">Orders</div>
                <div class="header-item">Paid orders</div>
                <div class="header-item">Cancelled orders</div>
            </c:if>
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
                    <c:if test="${tableTittle == 'Users'}">
                        <div class="table-data">${orders.stream().filter(order -> order.userId==user.getId()).toList().size()}</div>
                        <div class="table-data">${orders.stream().filter(order -> order.userId==user.getId()&&order.statusId==OrderStatus.PAID.getIndex()).toList().size()}</div>
                        <div class="table-data">${orders.stream().filter(order -> order.userId==user.getId()&&order.statusId==OrderStatus.CANCELED.getIndex()).toList().size()}</div>
                    </c:if>
                    <div class="table-data">${UserRole.getRole(user.roleId)}</div>
                    <div class="table-data" id="isBlocked-div${user.id}">${user.isBlocked()? 'yes' : 'no'}</div>
                    <div class="table-data" id="isBlocked-selector${user.id}" style="display: none">
                        <label>
                            <select name="isBlocked-status" id="${user.id}" onchange="changeStatus(this.id)">
                                <option selected disabled hidden></option>
                                <option value="true">yes</option>
                                <option value="false">no</option>
                            </select>
                        </label>
                    </div>

                    <div class="table-data" id="${user.id}" onclick=showSelector(this.id)>
                        <img id="editBtn" src="${pageContext.request.contextPath}/images/edit-icon.png"
                             alt="edit" width="20px" height="20px"/>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>


<script>
    function showSelector(clicked_id) {
        let nonSelectable = document.getElementById("isBlocked-div" + clicked_id)
        let selectable = document.getElementById("isBlocked-selector" + clicked_id)

        nonSelectable.style.display = "none"
        selectable.style.display = "block"
    }

    function changeStatus(clicked_id) {
        let nonSelectable = document.getElementById("isBlocked-div" + clicked_id)
        let selectable = document.getElementById("isBlocked-selector" + clicked_id)
        const confirmed = confirm("Save changes for user with id=" + clicked_id + "?");
        if (!confirmed) {
            nonSelectable.style.display = "block"
            selectable.style.display = "none"
            return
        }

        let statusSelector = document.getElementById(clicked_id)
        let value = statusSelector.options[statusSelector.selectedIndex].value

        let roleTemp = document.getElementById('roleName').innerText
        let role = roleTemp.substring(0, roleTemp.length - 1)

        fetch("${pageContext.request.contextPath}/my-travel?command=updateUser&userId=" + clicked_id + "&isBlocked=" + value, {
            method: "POST"
        })
            .then(response => {
                window.location.href = "${pageContext.request.contextPath}/my-travel?command=userEditor&role=" + role
            });
    }
</script>

</body>
</html>
