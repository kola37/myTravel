<%--
  Created by IntelliJ IDEA.
  User: Anatolii Koliaka
  Date: 07.06.2022
  Time: 15:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="entity.constant.TourType" %>

<html>
<c:set var="title" value="Home | My Travel Agency"/>
<c:set var="tours" value="${tours}"/>
<c:set var="hotels" value="${hotels}"/>
<c:set var="user" value="${user}" scope="session"/>
<c:set var="userRole" value="${userRole}" scope="session"/>
<c:set var="userLogin" value="${userLogin}" scope="session"/>
<c:set var="message" value="${message}" scope="session"/>
<c:set var="searchParamMessage" value="${paramMessage}"/>


<%@ include file="/WEB-INF/views/fragment/header.jsp" %>
<style>
    <%@ include file="/WEB-INF/styles/style.css" %>
</style>

<body>

<%--Greeting for users--%>
<c:if test="${userRole == 'user'}">
    <div class="info-msg">
        <h3><fmt:message key="home_jsp.greeting.user.hello"/> ${userLogin}!</h3>
        <hr>
    </div>
</c:if>

<%--Message for admin amd manager--%>
<c:if test="${userRole == 'admin' || userRole == 'manager'}">
    <div class="info-msg">
        <h3><fmt:message key="home_jsp.greeting.user.hello"/> ${userLogin}! <fmt:message
                key="home_jsp.greeting.admin.hello.begin"/>
                ${userRole} <fmt:message key="home_jsp.greeting.admin.hello.end"/></h3>
        <hr>
    </div>
</c:if>

<%--Searching parameters--%>
<c:if test="${not empty searchParamMessage}">
    <div class="info-msg" id="search-param">
        <h3><fmt:message key="home_jsp.searching_tour_param.msg.begin"/> ${searchParamMessage}
            <fmt:message key="home_jsp.searching_tour_param.msg.middle"/> ${tours.size()} <fmt:message
                    key="home_jsp.searching_tour_param.msg.end"/>
            <a class="closeBtn" id="search-param-close"
               href="${pageContext.request.contextPath}/my-travel?command=home">×</a></h3>
        <hr>
    </div>
</c:if>

<%--Information message after order confirmed or user edited--%>
<c:if test="${not empty message}">
    <div class="tour-order-box" id="alertMsg">
        <span class="closeBtn" id="closeAlert">×</span>
        <h2>${message}</h2>
        <c:remove var="message"/>
    </div>
</c:if>

<%--********************************************************************--%>
<%--Try to make pagination if tours.size() more than tours perPage value--%>
<%--********************************************************************--%>
<c:set var="totalCount" scope="session" value="${tours.size()}"/>
<c:set var="perPage" scope="session" value="8"/>
<c:set var="pageStart" value="${param.start}"/>
<c:if test="${empty pageStart or pageStart < 0}">
    <c:set var="pageStart" value="0"/>
</c:if>
<c:if test="${totalCount < pageStart}">
    <c:set var="pageStart" value="${pageStart - perPage}"/>
</c:if>
<%--Create tour card--%>
<c:forEach var="tour" items="${tours}" begin="${pageStart}" end="${pageStart + perPage - 1}">
    <div class="tour-container">
        <div class="tour-wrapper">

            <div class="tour-block">
                <h1><c:out value="${tour.name}"/></h1>
                <img src="<c:out value="${pageContext.request.contextPath}${tour.image}"/>" alt="image"/>

                <c:if test="${tour.isHot()}">
                    <div class="hot-image">
                        <img id="hot-img" src="${pageContext.request.contextPath}/images/hot-tour-icon.png" alt="hot">
                    </div>
                </c:if>

                <div class="info-block">
                    <h3><fmt:message key="home_jsp.tour_container.info_block.more_info"/></h3>
                    <p><fmt:message
                            key="home_jsp.tour_container.info_block.type"/> ${TourType.getType(tour.tourTypeId).getName()}</p>
                    <p><fmt:message
                            key="home_jsp.tour_container.info_block.hotel"/> ${hotels.stream().filter(hotel -> hotel.getId()==tour.hotelId).toList().get(0).name}</p>
                    <p><fmt:message key="home_jsp.tour_container.info_block.person"/> ${tour.numOfPersons}</p>
                    <p><fmt:message key="home_jsp.tour_container.info_block.price"/> ${tour.price} $</p>
                    <li class="more-info-btn">
                        <a href="${pageContext.request.contextPath}/my-travel?command=tourPage&tourId=${tour.id}"><fmt:message
                                key="home_jsp.tour_container.info_block.link.more_info"/></a>
                    </li>
                </div>
            </div>
        </div>
    </div>
</c:forEach>
<%--********************************************************************--%>
<%--          Pagination div with current showing tours info            --%>
<%--********************************************************************--%>
<div class="pagination-div">
    <c:if test="${(pageStart - perPage) >= 0}">
        <a href="${pageContext.request.contextPath}?start=${(pageStart - perPage) > 0 ? (pageStart - perPage) : 0}"><<</a>
    </c:if>
    <h3><fmt:message key="home_jsp.tour_container.page_show"/> ${pageStart + 1} - ${(pageStart + perPage) < totalCount ? pageStart + perPage : totalCount}
        <fmt:message key="home_jsp.tour_container.page_from"/> ${totalCount}</h3>
    <c:if test="${(pageStart + perPage) < totalCount}">
        <a href="${pageContext.request.contextPath}?start=${(pageStart + perPage) < totalCount ? (pageStart + perPage) : totalCount-1}">>></a>
    </c:if>
</div>

<%@ include file="/WEB-INF/views/fragment/footer.jsp" %>

<script>
    let alertMsg = document.getElementById("alertMsg")
    let closeAlert = document.getElementById("closeAlert")

    closeAlert.addEventListener("click", () => {
        alertMsg.style.display = "none"
    })
</script>

</body>
</html>
