<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="root" value="${pageContext.request.contextPath}" />
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                <form id="newJob-button-form" action="${root}/jobs/newJob" method="post">
                    <input class="coolButton" name="submit" size="60" value="Create new job" type="submit" style="margin-top: 70px">
                </form>
                <form id="messages-button-form" action="${root}/usr/customer/messages" method="post">
                    <input class="coolButton" name="submit" size="60" value="My messages" type="submit" style="margin-top: 10px">
                </form>
            </div>
            <div class="central-bar">
                <div align="center" style="margin-bottom: 20px; font-size: 30px"><b>My current jobs</b></div>
                <c:forEach items="${jobs}" var="job">
                    <div class="jobItem">
                        <div style="background: rgb(58,58,58); color: white">
                            <p style="text-align: center;
                                font-size: medium;
                                margin-top: auto;"><b>${job.title}</b></p>
                        </div>
                        <div style="margin-left: 10px; margin-right: 10px">
                            <b>Price: </b>${job.price} <br>
                            <b>Publish date: </b>${job.publishTime} <br>
                            <b>Deadline: </b>${job.deadline} <br>
                            <b>Tags: </b>${job.tags} <br><hr>
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
                <img id="userLogo" src="${root}/usr/customer/image/${userId}" style="width: 175px;
                                                                                     height: 175px;
                                                                                     margin-top: 10px;
                                                                                     border: solid 3px #4b4b4b;
                                                                                     border-radius: 10px 10px 10px 10px;"/>
                <div id="snf" style="margin-top: 20px; font-size: 15px"><b>${customerUser.snf}</b></div>
                <div id="email" style="margin-top: 5px; font-size: 15px"><b>${customerUser.email}</b></div>
                <div id="rating" style="margin-top: 5px; font-size: 15px"><b>Current rating: </b>${customerUser.rating}</div>
                <div id="birthday" style="margin-top: 5px; font-size: 15px"><b>${customerUser.birthday}</b></div>
                <form id="edit-profile-button-form" action="" method="post">
                    <input class="coolButton" name="submit" size="60" value="Edit my profile" type="submit" style="margin-top: 40px">
                </form>
            </div>
        </div>

        <%--include footer--%>
        <%@ include file="/view/public/common/footer.jsp" %>
    </div>
</body>
</html>
