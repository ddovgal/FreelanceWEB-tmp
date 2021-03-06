<%@ page import="com.project.businesslogic.user.User" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.project.businesslogic.Job" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="root" value="${pageContext.request.contextPath}" />
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>My profile</title>
    <link href="${root}/res/item-style.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="${root}/res/form.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="${root}/res/style.css" rel="stylesheet" type="text/css" media="screen" />

</head>
<body>
<div class="wrapper">

    <%--include header--%>
    <%@ include file="/view/public/common/header.jsp" %>

    <div class="content">
        <div class="left-bar" align="center">

        </div>
        <div class="central-bar">
            <div align="center" style="margin-bottom: 35px; font-size: 40px"><b>My current jobs</b></div>
            <c:forEach items="${jobs}" var="job">
                <div class="jobItem">
                    <c:if test="${job.finished}">
                        <div style="background: limegreen; color: white">
                    </c:if>
                    <c:if test="${!job.finished}">
                        <c:if test="${job.developerUser!=null}">
                        <div style="background: cornflowerblue; color: white">
                        </c:if>
                        <c:if test="${job.developerUser==null}">
                            <div style="background: rgb(58,58,58); color: white">
                        </c:if>
                    </c:if>
                        <p style="text-align: center;
                                font-size: medium;
                                margin-top: auto;"><b>${job.title}</b></p>
                    </div>
                    <div style="margin-left: 10px; margin-right: 10px">
                        <b>Price: </b>${job.price} <br>
                        <b>Publish date: </b>${job.publishTime} <br>

                        <%  Job job = (Job) pageContext.getAttribute("job");
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            String newFormatDeadline = sdf.format(job.getDeadline());
                        %>
                        <b>Deadline: </b><%=newFormatDeadline%><br>
                        <b>Tags: </b>${job.tags}<br><hr>
                        <b>Description: </b>${job.description}
                    </div>
                    <div align="center" style="margin-top: 10px; margin-bottom: 10px">
                        <form action="${root}/jobs/options" method="get">
                            <input type="hidden" name="jobId" value="${job.id}"/>
                            <button class="button_example" type="submit">See detail</button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="right-bar" align="center">
            <img id="userLogo" src="${root}/user/image/${userId}" style="width: 175px;
                                                                                     height: 175px;
                                                                                     margin-top: 10px;
                                                                                     border: solid 3px #4b4b4b;
                                                                                     border-radius: 10px 10px 10px 10px;"/>
            <div id="snf" style="margin-top: 20px; font-size: 15px"><b>${customerUser.snf}</b></div>
            <div id="email" style="margin-top: 5px; font-size: 15px"><b>${customerUser.email}</b></div>
            <div id="rating" style="margin-top: 5px; font-size: 15px"><b>Current rating: </b>${customerUser.rating}</div>

            <%  User user = (User) request.getAttribute("customerUser");
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String newFormatBirthday = sdf.format(user.getBirthday());
            %>
            <div id="birthday" style="margin-top: 5px; font-size: 15px"><b><%=newFormatBirthday%></b></div>

            <form id="newJob-button-form" action="${root}/jobs/newJob" method="get">
                <input class="coolButton" name="submit" size="60" value="Create new job" type="submit" style="margin-top: 40px">
            </form>
            <form id="edit-profile-button-form" action="${root}/usr/customer/profileDetail" method="get">
                <input class="coolButton" name="submit" size="60" value="Edit my profile" type="submit" style="margin-top: 10px">
                <input type="hidden" value="<sec:authentication property="principal.id" />" name="userId" />
            </form>
            <form id="messages-button-form" action="${root}/messages/show" method="post">
                <input class="coolButton" name="submit" size="60" value="My messages" type="submit" style="margin-top: 10px">
                <input type="hidden" value="<sec:authentication property="principal.id"/>" name="myId" />
            </form>

        </div>
    </div>

    <%--include footer--%>
    <%@ include file="/view/public/common/footer.jsp" %>
</div>
</body>
</html>
