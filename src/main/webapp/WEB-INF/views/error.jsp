<%--
  Created by IntelliJ IDEA.
  User: Anatolii Koliaka
  Date: 07.06.2022
  Time: 11:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<style>
    <%@include file="/WEB-INF/styles/error.css" %>
</style>
<head>
    <title>Error | My Travel Agency</title>
</head>
<body>

<%@ include file="/WEB-INF/views/fragment/header.jsp" %>

<h1>ERROR</h1>
<hr>

<h3>Sorry, something went wrong!</h3>

<%-- if we get this page using forward --%>
<c:if test="${not empty errorMessage}">
    <h3>${errorMessage}</h3>
</c:if>


</body>
</html>
