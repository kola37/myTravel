<%--
  Created by IntelliJ IDEA.
  User: Anatolii Koliaka
  Date: 11.06.2022
  Time: 13:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<c:set var="title" value="Login | My Travel Agency"/>

<%@ include file="/WEB-INF/views/fragment/header.jsp" %>
<style>
    <%@ include file="/WEB-INF/styles/login.css" %>
</style>

<body>


<%--Alert message for already logged in users--%>
<c:if test="${sessionScope.user != null}">
    <div class="tour-order-box" id="alertMsg">
        <a class="closeBtn" id="closeAlert" href=${pageContext.request.contextPath}/my-travel?command=home>×</a>
        <h2>Hi, ${sessionScope.userLogin}! You are already logged in!</h2>
    </div>
</c:if>
<c:if test="${sessionScope.user == null}">

    <div class="form-box">
        <div class="login-btn-box">
            <div id="btn"></div>
            <button type="button" class="toggle-btn" onclick="loginForm()">Login</button>
            <button type="button" class="toggle-btn" onclick="registerForm()">Register</button>
        </div>
        <form id="loginForm" class="input-group" action="${pageContext.request.contextPath}/my-travel?command=login"
              method="post">
            <label>
                <input class="input-field" type="text" placeholder="login" name="login" required>
            </label>
            <label>
                <input class="input-field" type="password" placeholder="password" name="password" required>
            </label>
            <button type="submit" class="submit-btn">Login</button>
        </form>


        <form id="registerForm" class="input-group"
              action="${pageContext.request.contextPath}/my-travel?command=register" method="post">
            <label>
                <input class="input-field" type="text" placeholder="login" name="login" required maxlength="15">
            </label>
            <label>
                <input class="input-field" type="password" placeholder="password" name="password" required
                       maxlength="15">
            </label>
            <label>
                <input class="input-field" type="text" placeholder="first name" name="firstName" required
                       maxlength="20">
            </label>
            <label>
                <input class="input-field" type="text" placeholder="last name" name="lastName" required maxlength="20">
            </label>
            <label>
                <input class="input-field" type="email" placeholder="email" name="email" required maxlength="40">
            </label>
            <label>
                    <%--        Set default role for new user--%>
                <input class="input-field" type="hidden" name="role" value="user" required>
            </label>
            <button type="submit" class="submit-btn">Register</button>
        </form>
    </div>

</c:if>

<script>
    var l = document.getElementById("loginForm");
    var r = document.getElementById("registerForm");
    var b = document.getElementById("btn");

    function registerForm() {
        l.style.left = "-400px";
        r.style.left = "50px";
        b.style.left = "110px";
    }

    function loginForm() {
        l.style.left = "50px";
        r.style.left = "450px";
        b.style.left = "0";
    }

</script>

</body>
</html>
