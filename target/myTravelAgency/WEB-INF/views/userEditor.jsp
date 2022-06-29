<%--
  Created by IntelliJ IDEA.
  User: Anatolii Koliaka
  Date: 23.06.2022
  Time: 17:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="entity.constant.UserRole" %>
<%@ page import="entity.constant.OrderStatus" %>


<html>
<c:set var="title" value="${tableTittle} editor | My Travel Agency"/>
<c:set var="userRole" value="${userRole}" scope="session"/>
<c:set var="userLogin" value="${userLogin}" scope="session"/>
<c:set var="users" value="${users}" scope="request"/>
<c:set var="orders" value="${orders}" scope="request"/>
<c:set var="tableTittle" value="${tableTittle}" scope="request"/>
<c:set var="commandNext" value="/my-travel?command=userEditor&role=manager" scope="request"/>

<%@ include file="/WEB-INF/views/fragment/header.jsp" %>
<style>
    <%@ include file="/WEB-INF/styles/userCabinet.css" %>
</style>

<body>

<%--Message for admin--%>
<c:if test="${userRole == 'admin'}">
    <div class="info-msg">
        <h3><fmt:message key="home_jsp.greeting.user.hello"/> ${userLogin}! <fmt:message key="home_jsp.greeting.admin.hello.begin"/>
            ${userRole} <fmt:message key="home_jsp.greeting.admin.hello.end"/></h3>
        <hr>
    </div>
</c:if>

<div class="tittle-container" id="main-view-tittle-container">
    <div class="tittle-item">
        <h2 id="roleName">${tableTittle}s</h2>
        <c:if test="${tableTittle == 'manager'}">
            <h2><a onclick="showNewManagerForm()"><fmt:message key="user_editor_jsp.container.link.add_new"/></a></h2>
        </c:if>
    </div>
</div>

<div class="container" id="main-view-container" action="">
    <div class="table">

        <div class="table-header">
            <div class="header-item"><fmt:message key="user_editor_jsp.container.column.id"/></div>
            <div class="header-item"><fmt:message key="user_editor_jsp.container.column.login"/></div>
            <div class="header-item"><fmt:message key="user_editor_jsp.container.column.first_name"/></div>
            <div class="header-item"><fmt:message key="user_editor_jsp.container.column.last_name"/></div>
            <div class="header-item"><fmt:message key="user_editor_jsp.container.column.email"/></div>
            <c:if test="${tableTittle == 'user'}">
                <div class="header-item"><fmt:message key="user_editor_jsp.container.column.orders"/></div>
                <div class="header-item"><fmt:message key="user_editor_jsp.container.column.paid_orders"/></div>
                <div class="header-item"><fmt:message key="user_editor_jsp.container.column.cancelled_orders"/></div>
            </c:if>
            <div class="header-item"><fmt:message key="user_editor_jsp.container.column.role"/></div>
            <div class="header-item"><fmt:message key="user_editor_jsp.container.column.blocked"/></div>
            <div class="header-item"><fmt:message key="user_editor_jsp.container.column.edit"/></div>
        </div>
        <div class="table-content">
            <c:forEach var="user" items="${users}">
                <div class="table-row">
                    <div class="table-data">${user.id}</div>
                    <div class="table-data">${user.login}</div>
                    <div class="table-data">${user.firstName}</div>
                    <div class="table-data">${user.lastName}</div>
                    <div class="table-data">${user.email}</div>
                    <c:if test="${tableTittle == 'user'}">
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
                                <option value="true"><fmt:message key="tour_editor_jsp.container.select.value.true"/></option>
                                <option value="false"><fmt:message key="tour_editor_jsp.container.select.value.false"/></option>
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


<div class="form-box" id="new-manager-form" style="display: none">
    <div>
        <h2><fmt:message key="user_editor_jsp.form.tittle"/></h2>
    </div>
    <form id="registerForm" class="input-group"
          action="${pageContext.request.contextPath}/my-travel?command=newManager" method="post">
        <label>
            <input class="input-field" type="text" placeholder="<fmt:message key="login_register_jsp.form.placeholder.login"/>" name="login" required maxlength="15">
        </label>
        <label>
            <input class="input-field" type="password" placeholder="<fmt:message key="login_register_jsp.form.placeholder.password"/>" name="password" required
                   maxlength="15">
        </label>
        <label>
            <input class="input-field" type="text" placeholder="<fmt:message key="login_register_jsp.form.placeholder.first_name"/>" name="firstName" required
                   maxlength="20">
        </label>
        <label>
            <input class="input-field" type="text" placeholder="<fmt:message key="login_register_jsp.form.placeholder.last_name"/>" name="lastName" required maxlength="20">
        </label>
        <label>
            <input class="input-field" type="email" placeholder="<fmt:message key="login_register_jsp.form.placeholder.email"/>" name="email" required maxlength="40">
        </label>
        <label>
            <input class="input-field" type="hidden" name="role" value="manager" required>
            <input type="hidden" name="commandNext" value="${commandNext}">
        </label>
        <button type="button" class="submit-btn" id="cancelBtn"><fmt:message key="user_editor_jsp.form.btn.cancel"/></button>
        <button type="submit" class="submit-btn"><fmt:message key="user_editor_jsp.form.btn.save"/></button>
    </form>
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
        const confirmed = confirm("<fmt:message key="user_editor_jsp.alert.msg.save_changes"/>" + clicked_id + "?");
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

    function showNewManagerForm() {
        let form = document.getElementById("new-manager-form")
        let mainViewTittle = document.getElementById("main-view-tittle-container")
        let mainViewContainer = document.getElementById("main-view-container")
        let cancelBtn = document.getElementById("cancelBtn")

        mainViewTittle.style.display = "none";
        mainViewContainer.style.display = "none";
        form.style.display = "block";

        cancelBtn.addEventListener("click", function(){
            fetch("${pageContext.request.contextPath}/my-travel?command=userEditor&role=manager", {method: "GET"})
                .then(response => {
                    window.location.href = "${pageContext.request.contextPath}/my-travel?command=userEditor&role=manager"
                });
        })
    }
</script>

</body>
</html>
