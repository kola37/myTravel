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
<c:set var="title" value="Error | My Travel Agency"/>
<c:set var="errorMessage" value="${errorMessage}" scope="request"/>
<body>

<%@ include file="/WEB-INF/views/fragment/header.jsp" %>

<h1>ERROR</h1>
<hr>
<%-- this way to obtain standart information about an exception that comes from command classes --%>
<c:if test="${not empty errorMessage}">
    <h3>${errorMessage}</h3>
</c:if>

<%-- this way we obtain an information about an exception (if it has been occurred) 404/500 etc.--%>
<c:set var="code" value="${requestScope['javax.servlet.error.status_code']}"/>
<c:set var="message" value="${requestScope['javax.servlet.error.message']}"/>

<c:if test="${not empty code}">
    <h3>Error code: ${code}</h3>
</c:if>

<c:if test="${not empty message}">
    <h3>${message}</h3>
</c:if>

</body>
</html>
