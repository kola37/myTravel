<%--
  Created by IntelliJ IDEA.
  User: Anatolii Koliaka
  Date: 10.06.2022
  Time: 16:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<c:set var="title" value="Login | My Travel Agency"/>

<%@ include file="/WEB-INF/views/fragment/header.jsp" %>
<style>
    <%@ include file="/WEB-INF/styles/login.css" %>
</style>

<body>

<div class="login">
    <h2>Please, enter your login and password</h2>
    <form class="form" action="${pageContext.request.contextPath}/my-travel?command=login" method="post">
        <fieldset class="username">
            <input type="text" placeholder="login" name="login"/>
        </fieldset>
<%--        <fieldset class="email">--%>
<%--            <input type="email" placeholder="email"/>--%>
<%--        </fieldset>--%>
        <fieldset class="password">
            <input type="password" placeholder="password"name="password"/>
        </fieldset>
        <button type="submit" class="btn">Login</button>
    </form>
</div>

</body>
</html>
