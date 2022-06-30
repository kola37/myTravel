<%--
  Created by IntelliJ IDEA.
  User: Anatolii Koliaka
  Date: 22.06.2022
  Time: 15:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="entity.constant.TourType" %>

<html>
<c:set var="title" value="Tour editor | My Travel Agency"/>
<c:set var="tours" value="${tours}"/>
<c:set var="users" value="${users}"/>


<%@ include file="/WEB-INF/views/fragment/header.jsp" %>
<style>
    <%@ include file="/WEB-INF/styles/userCabinet.css" %>
</style>
<body>

<%--Message for admin amd manager--%>
<c:if test="${userRole == 'admin' || userRole == 'manager'}">
    <div class="info-msg">
        <h3><fmt:message key="home_jsp.greeting.user.hello"/> ${userLogin}! <fmt:message key="home_jsp.greeting.admin.hello.begin"/>
                ${userRole} <fmt:message key="home_jsp.greeting.admin.hello.end"/></h3>
        <hr>
    </div>
</c:if>

<div class="tittle-container">
    <div class="tittle-item">
        <h2><fmt:message key="tour_editor_jsp.container.tittle"/></h2>
        <c:if test="${userRole == 'admin'}">
            <h2><a href="${pageContext.request.contextPath}/my-travel?command=addTour"><fmt:message key="tour_editor_jsp.container.btn.add_new"/></a></h2>
        </c:if>
    </div>
</div>


<div class="container">
    <div class="table">

        <div class="table-header">
            <div class="header-item"><fmt:message key="tour_editor_jsp.container.column.id"/></div>
            <div class="header-item"><fmt:message key="tour_editor_jsp.container.column.name"/></div>
            <div class="header-item"><fmt:message key="tour_editor_jsp.container.column.overview"/></div>
            <div class="header-item"><fmt:message key="tour_editor_jsp.container.column.image"/></div>
            <div class="header-item"><fmt:message key="tour_editor_jsp.container.column.price"/></div>
            <div class="header-item"><fmt:message key="tour_editor_jsp.container.column.type"/></div>
            <div class="header-item"><fmt:message key="tour_editor_jsp.container.column.person"/></div>
            <div class="header-item"><fmt:message key="tour_editor_jsp.container.column.hotel"/></div>
            <div class="header-item"><fmt:message key="tour_editor_jsp.container.column.disc_rate"/></div>
            <div class="header-item"><fmt:message key="tour_editor_jsp.container.column.max_disc"/></div>
            <div class="header-item"><fmt:message key="tour_editor_jsp.container.column.hot"/></div>
            <div class="header-item"><fmt:message key="tour_editor_jsp.container.column.edit"/></div>
            <c:if test="${userRole == 'admin'}">
                <div class="header-item"><fmt:message key="tour_editor_jsp.container.column.delete"/></div>
            </c:if>
        </div>
        <div class="table-content">
            <%--********************************************************************--%>
            <%--Try to make pagination if tours.size() more than tours perPage value--%>
            <%--********************************************************************--%>
            <c:set var="totalCount" scope="session" value="${tours.size()}"/>
            <c:set var="perPage" scope="session" value="15"/>
            <c:set var="pageStart" value="${param.start}"/>
            <c:if test="${empty pageStart or pageStart < 0}">
                <c:set var="pageStart" value="0"/>
            </c:if>
            <c:if test="${totalCount < pageStart}">
                <c:set var="pageStart" value="${pageStart - perPage}"/>
            </c:if>
            <c:forEach var="tour" items="${tours}" begin="${pageStart}" end="${pageStart + perPage - 1}">
                <div class="table-row">
                    <div class="table-data">${tours.indexOf(tour)+1}</div>
                    <div class="table-data">${tour.name}</div>
                    <div class="table-data" id="description">${tour.description}</div>
                    <div class="table-data">
                        <img id="tour-img" src="${pageContext.request.contextPath}${tour.image}" alt="img">
                    </div>
                    <div class="table-data">${tour.price}</div>
                    <div class="table-data">${TourType.getType(tour.tourTypeId)}</div>
                    <div class="table-data">${tour.numOfPersons}</div>
                    <div class="table-data">${hotels.stream().filter(hotel -> hotel.getId()==tour.hotelId).toList().get(0).name}</div>

                    <div class="table-data" id="discRate${tour.id}">${tour.discountRate}</div>
                    <div class="table-data" id="editDiscRate${tour.id}" style="display: none">
                        <form>
                            <label>
                                <input id="${tour.id}" type="number" min="0" max="100" name="discountRate"
                                       value="${tour.discountRate}"
                                       onchange="showSaveBtn(this.id)">
                            </label>
                        </form>
                    </div>

                    <div class="table-data" id="maxDisc${tour.id}">${tour.maxDiscount}</div>
                    <div class="table-data" id="editMaxDisc${tour.id}" style="display: none">
                        <form>
                            <label>
                                <input id="${tour.id}" type="number" min="0" max="100" name="maxDiscount"
                                       value="${tour.maxDiscount}"
                                       onchange="showSaveBtn(this.id)">
                            </label>
                        </form>
                    </div>

                    <div class="table-data" id="hot${tour.id}">${tour.isHot()? 'yes' : 'no'}</div>
                    <div class="table-data" id="editHot${tour.id}" style="display: none">
                        <label>
                            <select name="isHot" id="${tour.id}" onchange="showSaveBtn(this.id)">
                                <option selected disabled hidden></option>
                                <option value="true"><fmt:message key="tour_editor_jsp.container.select.value.true"/></option>
                                <option value="false"><fmt:message key="tour_editor_jsp.container.select.value.false"/></option>
                            </select>
                        </label>
                    </div>

                        <%--Edit button as visible default--%>
                    <div class="table-data" id="editDiv${tour.id}">
                        <div id="${tour.id}" onclick=editTour(this.id)>
                            <img id="editBtn" src="${pageContext.request.contextPath}/images/edit-icon.png"
                                 alt="edit" width="20px" height="20px"/>
                        </div>
                    </div>
                        <%--Save button visible when edit pressed--%>
                    <div class="table-data" id="saveDiv${tour.id}" style="display: none">
                        <div id="${tour.id}" onclick=saveChanges(this.id)>
                            <img id="saveBtn" src="${pageContext.request.contextPath}/images/save-icon.png"
                                 alt="save" width="20px" height="20px"/>
                        </div>
                    </div>
                    <c:if test="${userRole == 'admin'}">
                        <div class="table-data" id="${tour.id}" onclick=deleteTour(this.id)>
                            <img id="deleteBtn" src="${pageContext.request.contextPath}/images/delete-icon.png"
                                 alt="delete" width="20px" height="20px"/>
                        </div>
                    </c:if>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<%--********************************************************************--%>
<%--          Pagination div with current showing tours info            --%>
<%--********************************************************************--%>
<div class="pagination-div">
    <c:if test="${(pageStart - perPage) >= 0}">
        <a href="${pageContext.request.contextPath}/my-travel?command=tourEditor&start=${(pageStart - perPage) > 0 ? (pageStart - perPage) : 0}"><<</a>
    </c:if>
    <h3><fmt:message key="home_jsp.tour_container.page_show"/> ${pageStart + 1} - ${(pageStart + perPage) < totalCount ? pageStart + perPage : totalCount}
        <fmt:message key="home_jsp.tour_container.page_from"/> ${totalCount}</h3>
    <c:if test="${(pageStart + perPage) < totalCount}">
        <a href="${pageContext.request.contextPath}/my-travel?command=tourEditor&start=${(pageStart + perPage) < totalCount ? (pageStart + perPage) : totalCount-1}">>></a>
    </c:if>
</div>


<script>
    function deleteTour(clicked_id) {
        const confirmed = confirm("<fmt:message key="tour_editor_jsp.alert.msg.delete"/>" + clicked_id + "?");
        if (!confirmed) return;
        fetch("${pageContext.request.contextPath}/my-travel?command=deleteTour&tourId=" + clicked_id, {
            method: 'POST',
            tourId: clicked_id
        }).then(response => {
            window.location.href = "${pageContext.request.contextPath}/my-travel?command=tourEditor"
        });
    }

    function editTour(clicked_id) {
        let discRate = document.getElementById("discRate" + clicked_id);
        let editDiscRate = document.getElementById("editDiscRate" + clicked_id);
        let maxDisc = document.getElementById("maxDisc" + clicked_id);
        let editMaxDisc = document.getElementById("editMaxDisc" + clicked_id);
        let hot = document.getElementById("hot" + clicked_id);
        let editHot = document.getElementById("editHot" + clicked_id);

        ////////////////////////////////////////////////////////
        //manager can edit only 3 fields! Do it on current page
        ///////////////////////////////////////////////////////
        <c:if test="${userRole == 'manager'}">
        discRate.style.display = "none"
        editDiscRate.style.display = "block"
        maxDisc.style.display = "none"
        editMaxDisc.style.display = "block"
        hot.style.display = "none"
        editHot.style.display = "block"
        </c:if>
        ////////////////////////////////////////////////////////
        //admin can edit all fields! Do it on addTour.jsp page
        ///////////////////////////////////////////////////////
        <c:if test="${userRole == 'admin'}">
        fetch("${pageContext.request.contextPath}/my-travel?command=addTour&tourId=" + clicked_id)
            .then(response => {
                window.location.href = "${pageContext.request.contextPath}/my-travel?command=addTour&tourId=" + clicked_id
            });
        </c:if>
    }

    function showSaveBtn(clicked_id) {
        let editBtn = document.getElementById("editDiv" + clicked_id);
        let saveBtn = document.getElementById("saveDiv" + clicked_id);

        editBtn.style.display = "none"
        saveBtn.style.display = "block"
    }

    function saveChanges(clicked_id) {
        let editBtn = document.getElementById("editDiv" + clicked_id);
        let saveBtn = document.getElementById("saveDiv" + clicked_id);
        let discRate = document.getElementById("discRate" + clicked_id);
        let editDiscRate = document.getElementById("editDiscRate" + clicked_id);
        let maxDisc = document.getElementById("maxDisc" + clicked_id);
        let editMaxDisc = document.getElementById("editMaxDisc" + clicked_id);
        let hot = document.getElementById("hot" + clicked_id);
        let editHot = document.getElementById("editHot" + clicked_id);
        const confirmChanges = confirm("<fmt:message key="tour_editor_jsp.alert.msg.save_changes"/>" + clicked_id + "?");
        if (!confirmChanges) {
            editBtn.style.display = "block"
            saveBtn.style.display = "none"
            discRate.style.display = "block"
            editDiscRate.style.display = "none"
            maxDisc.style.display = "block"
            editMaxDisc.style.display = "none"
            hot.style.display = "block"
            editHot.style.display = "none"
            return
        }

        let discRateValue = editDiscRate.getElementsByTagName("input").namedItem("discountRate").value;
        let maxDiscValue = editMaxDisc.getElementsByTagName("input").namedItem("maxDiscount").value;
        let hotSelector = editHot.getElementsByTagName("select").namedItem("isHot").value;

        let options = {
            method: "POST"
        }
        fetch("${pageContext.request.contextPath}/my-travel?command=updateTour&tourId=" + clicked_id
            + "&discountRate=" + discRateValue
            + "&maxDiscount=" + maxDiscValue
            + "&isHot=" + hotSelector,
            options)
            .then(response => {
                window.location.href = "${pageContext.request.contextPath}/my-travel?command=tourEditor"
            })
    }
</script>

</body>
</html>
