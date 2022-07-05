<%--
  Created by IntelliJ IDEA.
  User: Anatolii Koliaka
  Date: 21.06.2022
  Time: 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.mytravel.entity.constant.OrderStatus" %>

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
        <h3><fmt:message key="home_jsp.greeting.user.hello"/> ${userLogin}! <fmt:message
                key="home_jsp.greeting.admin.hello.begin"/>
                ${userRole} <fmt:message key="home_jsp.greeting.admin.hello.end"/></h3>
        <hr>
    </div>
</c:if>

<div class="tittle-container">
    <div class="tittle-item">
        <h2><fmt:message key="order_editor_jsp.container.tittle"/></h2>
    </div>
</div>

<div class="container">
    <div class="table">

        <div class="table-header">
            <div class="header-item"><fmt:message key="order_editor_jsp.container.column.order"/></div>
            <div class="header-item"><fmt:message key="order_editor_jsp.container.column.date"/></div>
            <div class="header-item"><fmt:message key="order_editor_jsp.container.column.tour"/></div>
            <div class="header-item"><fmt:message key="order_editor_jsp.container.column.hotel"/></div>
            <div class="header-item"><fmt:message key="order_editor_jsp.container.column.client"/></div>
            <div class="header-item"><fmt:message key="order_editor_jsp.container.column.discount"/></div>
            <div class="header-item"><fmt:message key="order_editor_jsp.container.column.total"/></div>
            <div class="header-item"><fmt:message key="order_editor_jsp.container.column.status"/></div>
            <div class="header-item"><fmt:message key="order_editor_jsp.container.column.edit"/></div>
            <div class="header-item"><fmt:message key="order_editor_jsp.container.column.delete"/></div>
        </div>
        <div class="table-content">
            <%--********************************************************************--%>
            <%--Try to make pagination if tours.size() more than tours perPage value--%>
            <%--********************************************************************--%>
            <c:set var="totalCount" scope="session" value="${orders.size()}"/>
            <c:set var="perPage" scope="session" value="15"/>
            <c:set var="pageStart" value="${param.start}"/>
            <c:if test="${empty pageStart or pageStart < 0}">
                <c:set var="pageStart" value="0"/>
            </c:if>
            <c:if test="${totalCount < pageStart}">
                <c:set var="pageStart" value="${pageStart - perPage}"/>
            </c:if>
            <c:forEach var="order" items="${orders}" begin="${pageStart}" end="${pageStart + perPage - 1}">
                <div class="table-row">
                    <div class="table-data">${orders.indexOf(order)+1}</div>
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
                                <option disabled selected><fmt:message
                                        key="order_editor_jsp.container.select.choose_status"/></option>
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
<%--********************************************************************--%>
<%--          Pagination div with current showing orders info            --%>
<%--********************************************************************--%>
<div class="pagination-div">
    <c:if test="${(pageStart - perPage) >= 0}">
        <a href="${pageContext.request.contextPath}/my-travel?command=orderEditor&start=${(pageStart - perPage) > 0 ? (pageStart - perPage) : 0}"><<</a>
    </c:if>
    <h3><fmt:message key="order_editor_jsp.container.page_show"/> ${pageStart + 1} - ${(pageStart + perPage) < totalCount ? pageStart + perPage : totalCount}
        <fmt:message key="order_editor_jsp.container.page_from"/> ${totalCount}</h3>
    <c:if test="${(pageStart + perPage) < totalCount}">
        <a href="${pageContext.request.contextPath}/my-travel?command=orderEditor&start=${(pageStart + perPage) < totalCount ? (pageStart + perPage) : totalCount-1}">>></a>
    </c:if>
</div>

<%@ include file="/WEB-INF/views/fragment/footer.jsp" %>

<script>
    function deleteOrder(clicked_id) {
        const confirmed = confirm("<fmt:message key="order_editor_jsp.alert.msg.delete"/>" + clicked_id + "?");
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
        const confirmed = confirm("<fmt:message key="order_editor_jsp.alert.msg.save_changes"/>" + clicked_id + "?");
        if (!confirmed) {
            nonSelectable.style.display = "block"
            selectable.style.display = "none"
            return
        }

        let statusSelector = document.getElementById(clicked_id)
        let value = statusSelector.options[statusSelector.selectedIndex].value

        let options = {
            method: "POST"
        }
        fetch("${pageContext.request.contextPath}/my-travel?command=updateOrder&orderId=" + clicked_id + "&statusId=" + value, options)
            .then(response => {
                window.location.href = "${pageContext.request.contextPath}/my-travel?command=orderEditor"
            })
    }
</script>

</body>
</html>
