<%--
  Created by IntelliJ IDEA.
  User: Anatolii Koliaka
  Date: 21.06.2022
  Time: 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="entity.constant.OrderStatus" %>

<html>
<c:set var="title" value="Order editor | My Travel Agency"/>
<c:set var="userRole" value="${userRole}" scope="session"/>
<c:set var="userLogin" value="${userLogin}" scope="session"/>
<c:set var="orders" value="${orders}"/>
<c:set var="users" value="${users}"/>

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
        <h2>Orders</h2>
    </div>
</div>

<div class="container">
    <div class="table">

        <div class="table-header">
            <div class="header-item">Order#</div>
            <div class="header-item">Date</div>
            <div class="header-item">Tour</div>
            <div class="header-item">Hotel</div>
            <div class="header-item">Client</div>
            <div class="header-item">Discount</div>
            <div class="header-item">Total</div>
            <div class="header-item">Status</div>
            <div class="header-item">Edit</div>
            <div class="header-item">Delete</div>
        </div>
        <div class="table-content">
            <c:forEach var="order" items="${orders}">
                <div class="table-row">
                    <div class="table-data">${order.id}</div>
                    <div class="table-data">${order.orderDate}</div>
                    <div class="table-data">${tours.stream().filter(tour -> tour.getId()==order.tourId).toList().get(0).name}</div>
                    <div class="table-data">${hotels.stream().filter(hotel -> hotel.getId()==tours.stream().filter(tour -> tour.getId()==order.tourId).toList().get(0).hotelId).toList().get(0).name}</div>
                    <div class="table-data">${users.stream().filter(user -> user.getId()==order.userId).toList().get(0).login}</div>
                    <div class="table-data">${order.discount}</div>
                    <div class="table-data">${order.totalPrice}</div>
                    <div class="table-data" id="nonSelectable${order.id}">${OrderStatus.getStatus(order.statusId)}</div>
                    <div class="table-data" id="selectable${order.id}" style="display: none">
                        <label>
                            <select name="order_status" id="${order.id}" onchange="changeStatus(this.id)">
                                <option disabled selected>choose status</option>
                                <option value="1">${OrderStatus.getStatus(1).name()}</option>
                                <option value="2">${OrderStatus.getStatus(2).name()}</option>
                                <option value="3">${OrderStatus.getStatus(3).name()}</option>
                            </select>
                        </label>
                    </div>
                    <div class="table-data" id="${order.id}" onclick=showSelector(this.id)>
                        <img id="editBtn" src="${pageContext.request.contextPath}/images/edit-icon.png"
                             alt="edit" width="20px" height="20px"/>
                    </div>
                    <div class="table-data" id="${order.id}" onclick=deleteOrder(this.id)>
                        <img id="deleteBtn" src="${pageContext.request.contextPath}/images/delete-icon.png"
                             alt="delete" width="20px" height="20px"/>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>


<script>
    function deleteOrder(clicked_id) {
        const confirmed = confirm("Delete order #" + clicked_id + "?");
        if (!confirmed) return;
        fetch("${pageContext.request.contextPath}/my-travel?command=deleteOrder&orderId=" + clicked_id, {
            method: 'POST',
            orderId: clicked_id
        }).then(response => {
            window.location.href = "${pageContext.request.contextPath}/my-travel?command=orderEditor"
        });
    }

    function showSelector(clicked_id) {
        let nonSelectable = document.getElementById("nonSelectable" + clicked_id)
        let selectable = document.getElementById("selectable" + clicked_id)

        nonSelectable.style.display = "none"
        selectable.style.display = "block"
    }

    function changeStatus(clicked_id) {
        let nonSelectable = document.getElementById("nonSelectable" + clicked_id)
        let selectable = document.getElementById("selectable" + clicked_id)
        const confirmed = confirm("Save changes in order #" + clicked_id + "?");
        if (!confirmed) {
            nonSelectable.style.display = "block"
            selectable.style.display = "none"
            return
        }

        let statusSelector = document.getElementById(clicked_id)
        let value = statusSelector.options[statusSelector.selectedIndex].value

        // let orderData = {orderId: clicked_id, orderStatus: value}
        let options = {
            method: "POST"
        }
        fetch("${pageContext.request.contextPath}/my-travel?command=updateOrder&orderId=" + clicked_id +"&statusId=" + value, options)
            .then(response => {
                window.location.href = "${pageContext.request.contextPath}/my-travel?command=orderEditor"
            })

        <%--fetch("${pageContext.request.contextPath}/my-travel?command=updateOrder", {--%>
        <%--    method: "POST",--%>
        <%--    headers: {--%>
        <%--        Accept: "application/json",--%>
        <%--        "Content-Type": "application/json; charset=UTF-8",--%>
        <%--    },--%>
        <%--    body: JSON.stringify({ orderId: clicked_id }),--%>
        <%--}).then(response => {--%>
        <%--    window.location.href = "${pageContext.request.contextPath}/my-travel?command=orderEditor"--%>
        <%--});--%>
    }
</script>

</body>
</html>
