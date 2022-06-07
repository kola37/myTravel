<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isErrorPage="true" %>
<%@ page import="java.io.PrintWriter" %>


<html>

<c:set var="title" value="Error" scope="page" />

<body>
	<table id="main-container">
		<tr >
			<td class="content">
			<%-- CONTENT --%>
				<h2 class="error">
					The following error occurred
				</h2>

				<%-- this way we obtain an information about an exception (if it has been occurred) --%>
				<c:set var="code" value="${requestScope['javax.servlet.error.status_code']}"/>
				<c:set var="message" value="${requestScope['javax.servlet.error.message']}"/>
				<c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>

				<c:if test="${not empty code}">
				   <c:set var="code" value="${fn:replace(code, ' ', '')}" />
				      <c:if test="${not empty code}">
					    <h3>Error code: ${code}</h3>
					  </c:if>
				</c:if>

				<c:if test="${not empty message}">
					<h3>${message}</h3>
				</c:if>


				<%-- if we get this page using forward --%>
				<c:if test="${not empty requestScope.errorMessage}">
					<h3>${requestScope.errorMessage}</h3>
				</c:if>

				<h3>${myMessage}</h3>
				<%= request.getAttribute("errorMessage") %>
				<br/>
				<%= request.getAttribute("message") %>

			<%-- CONTENT --%>
			</td>
		</tr>



	</table>

	<c:set var="myName" value="Alex"/>
	<%
        pageContext.setAttribute("myName", "Tom");
    %>
	<h1>${myName}</h1>
	<c:out value="${ myName }" />

</body>
</html>