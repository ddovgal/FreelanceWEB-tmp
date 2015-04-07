<%@ page import="com.project.businesslogic.Job" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="root" value="${pageContext.request.contextPath}" />

<head>
    <title>Job page</title>
    <link href="${root}/res/filter-form-style.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="${root}/res/style.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="${root}/res/item-style.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="${root}/res/form.css" rel="stylesheet" type="text/css" media="screen" />

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
                                url: '/FreeCoin/jobs/remove/applicant',
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
                    <div style="margin-top: 5px">
                        <form id="cancelAppliance-button-form" style="display: inline-block">
                            <button class="button_example" id="cancelApplyForJobButton" onclick="removeApplicant()" style="margin-top: 10px; margin-right: 5px">Cancel appliance</button>
                        </form>
                        <form id="messageCustomer-button-form" action="${root}/messages/show_for/${customerUser.id}" method="get"  style="display: inline-block">
                            <input class="button_example" value="Message" type="submit" style="margin-top: 10px; margin-left: 5px">
                        </form>
                    </div>
                </c:if>
                <c:if test="${!isApplicant}" >
                    <script>
                        function applyForJobRequest(devId, jobId) {
                            $.ajax({
                                type: 'PUT',
                                url: '/FreeCoin/jobs/add/applicant',
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
                    <div style="margin-top: 5px">
                        <form id="applyForJob-button-form" style="display: inline-block">
                            <button class="button_example" id="applyForJobButton" onclick="addApplicant()" style="margin-top: 10px; margin-right: 5px">Apply for job</button>
                        </form>
                        <form id="messageCustomer-button-form-applied" action="${root}/messages/show_for/${customerUser.id}" method="get"  style="display: inline-block">
                            <input class="button_example" value="Message" type="submit" style="margin-top: 10px; margin-left: 5px">
                        </form>
                    </div>
                </c:if>
            </c:if>
            <c:if test="${isAdmin}">
                <%--some logic for admin--%>
            </c:if>
            <c:if test="${isCustomer}">
                <%--some logic for customer--%>
                <c:if test="${isMine}">
                    <c:if test="${isOpen}">
                        <c:forEach items="${applicants}" var="applicant">
                            <div class="applicantItem">
                                <img align="left" id="userLogo" src="${root}/user/image/${applicant.id}" style="width: 75px;
                                                                                     height: 75px;
                                                                                     border: solid 2px #4b4b4b;
                                                                                     border-radius: 5px 5px 5px 5px;"/>
                                <p style="margin-top: 0px; font-size: 15px">
                                <table style="color: #000000">
                                    <tr>
                                        <td style="padding-left: 10px"><b>${applicant.snf}</b></td>
                                    </tr>
                                    <tr>
                                        <td style="padding-left: 10px"><b>His rating: ${applicant.rating}</b></td>
                                    </tr>
                                </table>
                                </p>
                                <div align="center" style="margin-top: 5px">
                                    <form id="accept-button-form" action="${root}/jobs/add/developer" method="post" style="display: inline-block">
                                        <input class="button_example" value="Choose" type="submit" style="margin-top: 10px; margin-right: 5px">
                                        <input type="hidden" value="${job.id}" name="jobId" />
                                        <input type="hidden" value="${applicant.id}" name="developerId" />
                                    </form>
                                    <form id="messageApplicant-button-form" action="${root}/messages/show_for/${applicant.id}" method="get"  style="display: inline-block">
                                        <input class="button_example" value="Message" type="submit" style="margin-top: 10px; margin-left: 5px">
                                    </form>
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>
                    <c:if test="${!isOpen}">
                        <div class="applicantItem" style="border: solid 3px limegreen">
                            <img align="left" id="developerLogo" src="${root}/user/image/${developerUser.id}" style="width: 75px;
                                                                                     height: 75px;
                                                                                     border: solid 2px #4b4b4b;
                                                                                     border-radius: 5px 5px 5px 5px;"/>
                            <p style="margin-top: 0px; font-size: 15px">
                            <table style="color: #000000">
                                <tr>
                                    <td style="padding-left: 10px"><b>${developerUser.snf}</b></td>
                                </tr>
                                <tr>
                                    <td style="padding-left: 10px"><b>His rating: ${developerUser.rating}</b></td>
                                </tr>
                            </table>
                            </p>
                            <div align="center" style="margin-top: 5px">
                                <form id="dismiss-button-form" action="${root}/jobs/remove/developer" method="post" style="display: inline-block">
                                    <input class="button_example" value="Dismiss" type="submit" style="margin-top: 10px; margin-right: 5px">
                                    <input type="hidden" value="${job.id}" name="jobId" />
                                    <input type="hidden" value="${developerUser.id}" name="developerId" />
                                </form>
                                <form id="messageDeveloper-button-form" action="${root}/messages/show_for/${developerUser.id}" method="GET"  style="display: inline-block">
                                    <input class="button_example" value="Message" type="submit" style="margin-top: 10px; margin-left: 5px">
                                </form>
                            </div>
                        </div>
                    </c:if>
                </c:if>
            </c:if>
        </div>
        <div class="central-bar" style="margin-left: 15px">
            <div style="font-size: larger; margin-left: 8px">
                <div style="background: rgb(58,58,58); color: white; border-radius: 10px 10px 10px 10px">
                    <p style="text-align: center;
                                font-size: medium;
                                margin-top: auto;"><b>${job.title}</b></p>
                </div>
                <div style="margin-left: 10px; margin-right: 10px; margin-top: 10px">
                    <b>Price: </b>${job.price} <br>
                    <b>Publish date: </b>${job.publishTime} <br>

                    <%  Job job = (Job) request.getAttribute("job");
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        String newFormatDeadline = sdf.format(job.getDeadline());
                    %>
                    <b>Deadline: </b><%=newFormatDeadline%><br>
                    <b>Tags: </b>${job.tags}<br><hr>
                    <b>Description: </b>${job.description}<br><hr>
                    <b>Agreement: </b>${job.agreement}
                </div>
            </div>
        </div>
        <div class="right-bar" align="center">

            <c:if test="${isDeveloper}">
                <%--some logic for developer--%>
            </c:if>
            <c:if test="${isAdmin}">
                <%--some logic for admin--%>
            </c:if>
            <c:if test="${isCustomer}">
                <%--some logic for customer--%>
                <c:if test="${isMine}">
                    <form id="edit-button-form" action="${root}/jobs/editJob" method="get">
                        <input class="coolButton" size="60" value="Edit this job" type="submit" style="margin-top: 40px">
                        <input type="hidden" value="${job.id}" name="jobId" />
                    </form>
                    <form id="delete-button-form" action="${root}/jobs/deleteJob" method="post">
                        <input class="coolButton" size="60" value="Delete this job" type="submit" style="margin-top: 10px">
                        <input type="hidden" value="${job.id}" name="jobId" />
                    </form>
                    <form id="setFinished-button-form" action="${root}/jobs/setFinished" method="post">
                        <input class="coolButton" size="60" value="Set finished" type="submit" style="margin-top: 10px">
                        <input type="hidden" value="${job.id}" name="jobId" />
                    </form>
                </c:if>
            </c:if>
        </div>
    </div>

    <%--include footer--%>
    <%@ include file="/view/public/common/footer.jsp" %>
</div>


</body>
</html>