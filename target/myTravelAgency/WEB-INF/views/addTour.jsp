<%--
  Created by IntelliJ IDEA.
  User: Anatolii Koliaka
  Date: 22.06.2022
  Time: 17:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.mytravel.entity.constant.TourType" %>

<html>
<c:set var="title" value="Add tour | My Travel Agency"/>
<c:set var="tour" value="${tour}"/>
<c:set var="commandNext" value="/my-travel?command=tourEditor" scope="request"/>

<%@ include file="/WEB-INF/views/fragment/header.jsp" %>
<style>
    <%@ include file="/WEB-INF/styles/addEntity.css" %>
</style>


<%--If tour variable is not empty try to load data for selectors--%>
<body <c:if test="${not empty tour}">
    onload="selectElement('hotel-selector','${tour.hotelId}');
    selectElement('type-selector', '${TourType.getType(tour.tourTypeId).name()}')"
</c:if>>


<div class="form-box">
    <div>
        <h2><fmt:message key="addTour_jsp.input.tittle.tittle"/></h2>
    </div>
    <%--***********************************************************************--%>
    <%-- For uploading images need to set form enctype to "multipart/form-data"--%>
    <%--***********************************************************************--%>
    <form class="input-group"
          action="${pageContext.request.contextPath}/my-travel?command=newTour" method="post"
          enctype="multipart/form-data">
        <p><fmt:message key="addTour_jsp.input.tittle.name"/></p>
        <label>
            <input class="input-field" type="text" placeholder="<fmt:message key="addTour_jsp.input.placeholder.name"/>" name="name" required maxlength="50"
                   value="${tour.name}">
        </label>
        <p><fmt:message key="addTour_jsp.input.tittle.description"/></p>
        <label>
            <input class="input-field" type="text" placeholder="<fmt:message key="addTour_jsp.input.placeholder.description"/>" name="description"
                   maxlength="500" value="${tour.description}">
        </label>
        <p><fmt:message key="addTour_jsp.input.tittle.price"/></p>
        <label>
            <input class="input-field" type="number" placeholder="<fmt:message key="addTour_jsp.input.placeholder.price"/>" name="price" min="0" required
                   value="${tour.price}">
        </label>
        <p><fmt:message key="addTour_jsp.input.tittle.person"/></p>
        <label>
            <input class="input-field" type="number" placeholder="<fmt:message key="addTour_jsp.input.placeholder.person"/>" name="numOfPersons" min="0" required
                   value="${tour.numOfPersons}">
        </label>
        <p><fmt:message key="addTour_jsp.input.tittle.type"/></p>
        <label>
            <select class="input-field" id="type-selector" required name="tourType">
                <option value="${TourType.HOLIDAYS.name()}" selected>${TourType.HOLIDAYS.name()}</option>
                <option value="${TourType.EXCURSION.name()}">${TourType.EXCURSION.name()}</option>
                <option value="${TourType.SHOPPING.name()}">${TourType.SHOPPING.name()}</option>
            </select>
        </label>
        <p><fmt:message key="addTour_jsp.input.tittle.hotel"/></p>
        <label>
            <select class="input-field" id="hotel-selector" name="hotelName">
                <c:forEach var="hotel" items="${hotels}">
                    <option value="${hotel.id}">${hotel.name}</option>
                </c:forEach>
            </select>
        </label>

        <div class="slider-plus-image-div">
            <div class="slider-div">
                <label>
                    <input class="input-field" type="number" placeholder="<fmt:message key="addTour_jsp.input.placeholder.disc_rate"/>" min="0" max="100"
                           name="discountRate"
                           value="${tour.discountRate}">
                </label>
                <label>
                    <input class="input-field" type="number" min="0" max="100" placeholder="<fmt:message key="addTour_jsp.input.placeholder.max_disc"/>"
                           name="maxDiscount"
                           value="${tour.maxDiscount}">
                </label>
                <p><fmt:message key="addTour_jsp.input.tittle.hot_tour"/></p>
                <label class="switch">
                    <%--When new tour is creating show swith--%>
                    <c:if test="${empty tour}">
                        <input id="hot-switch" type="checkbox" name="isHot" value="true">
                    </c:if>
                    <%--When tour is updating show swith--%>
                    <c:if test="${tour.isHot() == 'true'}">
                        <input id="hot-switch" type="checkbox" name="isHot" value="true" checked>
                    </c:if>
                    <c:if test="${tour.isHot() == 'false'}">
                        <input id="hot-switch" type="checkbox" name="isHot" value="true">
                    </c:if>
                    <span class="slider round"></span>
                </label>
            </div>
            <label>
                <input type="hidden" name="tourId" value="${tour.id}">
                <input type="hidden" name="commandNext" value="${commandNext}">
            </label>

            <%--Container with tour image--%>
            <div class="image-div">
                <c:if test="${not empty tour}">
                    <img id="image-img" src="${pageContext.request.contextPath}${tour.image}" alt="tour_picture">
                </c:if>
                <c:if test="${empty tour}">
                    <img id="image-img" src="${pageContext.request.contextPath}/images/default-icon.png"
                         alt="tour_picture">
                </c:if>
            </div>
            <input id="image-input" type="file" name="file" accept="image/png, image/jpg">
        </div>
        <button type="submit" class="submit-btn"><fmt:message key="addTour_jsp.input.submit.btn.save"/></button>
    </form>
</div>


<script>
    function selectElement(id, valueToSelect) {
        let element = document.getElementById(id);
        element.value = valueToSelect;
        element.dispatchEvent(new Event('change'))
    }

    const image_input = document.getElementById("image-input");
    const imgContainer = document.getElementById("image-img");
    image_input.addEventListener("change", function () {
        const file = this.files[0];
        const reader = new FileReader();
        reader.addEventListener("load", function () {
            imgContainer.setAttribute("src", this.result);
        })
        reader.readAsDataURL(file);
    })
</script>

</body>
</html>
