<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="root" value="${pageContext.request.contextPath}" />

<head>
    <title>Job page</title>
    <link href="${root}/res/filter-form-style.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="${root}/res/style.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="${root}/res/item-style.css" rel="stylesheet" type="text/css" media="screen" />

    <script src="${root}/res/js/JQuery-1.10.2.js" ></script>
</head>
<body>
<div class="wrapper">

    <%--include header--%>
    <%@ include file="/view/public/common/header.jsp" %>

    <div class="content">
        <div class="left-bar">

            <c:if test="${isDeveloper}">
                <c:if test="${isApplicant}" >
                    <script>
                        function cancelApplianceForJobRequest(devId, jobId) {
                            $.ajax({
                                type: 'PUT',
                                url: '${root}/jobs/remove/applicant',
                                data: { devId: devId,
                                    jobId: jobId }
                            });
                        }
                        function removeApplicant() {
                            alert("click")
                            cancelApplianceForJobRequest('<sec:authentication property="principal.id" />',
                                    ${job.id});
                            location.reload();
                        }
                    </script>
                    <h5>You have already applied for this job!</h5>
                    <button class="button_example" id="cancelApplyForJobButton" onclick="removeApplicant()">Cancel appliance</button>
                </c:if>
                <c:if test="${!isApplicant}" >
                    <script>
                        function applyForJobRequest(devId, jobId) {
                            $.ajax({
                                type: 'PUT',
                                url: '${root}/jobs/add/applicant',
                                data: { devId: devId,
                                    jobId: jobId }
                            });
                        }
                        function addApplicant() {
                            alert("click")
                            applyForJobRequest('<sec:authentication property="principal.id" />',
                                    ${job.id});
                            location.reload();
                        }
                    </script>
                    <h5>You haven't already applied for this job!</h5>
                    <button class="button_example" id="applyForJobButton" onclick="addApplicant()">Apply for job</button>
                </c:if>
            </c:if>
            <c:if test="${isAdmin}">
                <%--some logic for admin--%>
            </c:if>
            <c:if test="${isCustomer}">
                <%--some logic for customer--%>
                <c:forEach items="${applicants}" var="applicant">
                    <div class="applicantItem">
                        <img id="userLogo" src="${root}/usr/developer/image/${userId}" style="width: 50px;
                                                                                     height: 50px;
                                                                                     border: solid 2px #4b4b4b;
                                                                                     border-radius: 5px 5px 5px 5px;"/>
                        <table style="color: #000000">
                            <tr>
                                <td><b>${applicant.snf}</b></td>
                            </tr>
                            <tr>
                                <td><b>His current rating: ${applicant.rating}</b></td>
                            </tr>
                            <tr>
                                <div align="center" style="margin-top: 5px">
                                    <td><button id="acceptHim"  class="button_example" type="submit" >Chose him</button></td>
                                </div>
                            </tr>
                        </table>
                    </div>
                </c:forEach>
            </c:if>
        </div>
        <div class="central-bar">
            <h3>${job.title}</h3>
            Price: ${job.price} <br>
            Publish date: ${job.publishTime} <br>
            Deadline: ${job.deadline} <br>
            Tags: ${job.tags} <br><hr>
            Description: ${job.description} <br><hr>
            Agreement: ${job.agreement}
        </div>
        <div class="right-bar">

            <c:if test="${isDeveloper}">
                <%--some logic for developer--%>
            </c:if>
            <c:if test="${isAdmin}">
                <%--some logic for admin--%>
            </c:if>
            <c:if test="${isCustomer}">
                <%--some logic for customer--%>
            </c:if>

        </div>
    </div>

    <%--include footer--%>
    <%@ include file="/view/public/common/footer.jsp" %>
</div>


</body>
</html>