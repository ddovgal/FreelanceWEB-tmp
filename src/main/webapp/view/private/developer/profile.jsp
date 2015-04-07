<%@ page import="com.project.businesslogic.user.User" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Developer profile</title>
    <link href="${root}/res/style.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>
    <div class="wrapper">

        <%--include header--%>
        <%@ include file="/view/public/common/header.jsp" %>

        <div class="content">
            <div class="left-bar">
                <h4>Applicable Jobs</h4>
                <c:forEach items="${applicableJobs}" var="job">
                    <div class="jobItem" style="font-size: smaller; margin-left: 8px; width: 200px;">
                        <div style="background: rgb(58,58,58); color: white; border-radius: 7px 7px 7px 7px">
                            <p style="text-align: center;
                                font-size: medium;
                                margin-top: auto;"><b>${job.title}</b></p>
                        </div>
                        <div style="margin-left: 10px; margin-right: 10px">
                            <b>Price: </b>${job.price} <br>
                            <b>Publish date: </b>${job.publishTime} <br>
                            <b>Deadline: </b>${job.deadline} <br>
                        </div>
                        <form action="${root}/jobs/options" method="get" style="margin-top: 10px; float: bottom">
                            <div align="center">
                                <input type="hidden" name="jobId" value="${job.id}"/>
                                <button class="button_example" type="submit">See details</button>
                            </div>
                        </form>
                    </div>
                </c:forEach>
            </div>
            <div class="central-bar">
                <div id="profileData" align="center">
                    <img id="userLogo" src="${root}/user/image/${developerUser.id}" style="width: 175px;
                                                                                     height: 175px;
                                                                                     margin-top: 10px;
                                                                                     border: solid 3px #4b4b4b;
                                                                                     border-radius: 10px 10px 10px 10px;"/>
                    <h2>${developerUser.snf}</h2>
                    <p><b>Email: </b>${developerUser.email}</p>

                    <%  User user = (User) request.getAttribute("developerUser");
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        String newFormatBirthday = sdf.format(user.getBirthday());
                    %>
                    <p><b>DOB: </b><%=newFormatBirthday%></p>
                    <p><b>Rating: </b>${developerUser.rating}</p>
                    <p><b>Skills: </b>${developerUser.skills}</p>
                    <form id="edit-profile-button-form" action="${root}/usr/developer/profileDetail" method="get">
                        <input class="button_blue" name="submit" size="60" value="Edit my profile" type="submit" style="margin-top: 40px">
                        <input type="hidden" value="<sec:authentication property="principal.id" />" name="userId" />
                    </form>
                    <form id="messages-button-form" action="${root}/messages/show" method="post">
                        <input class="button_blue" name="submit" size="60" value="My messages" type="submit">
                        <input type="hidden" value="<sec:authentication property="principal.id"/>" name="myId" />
                    </form>
                </div>
            </div>
            <div class="right-bar">
                <h4>Current jobs</h4>
                <c:forEach items="${currentJobs}" var="job">
                    <div class="jobItem" style="font-size: smaller; margin-left: 8px; width: 200px;">
                        <div style="background: forestgreen; color: white; border-radius: 7px 7px 7px 7px">
                            <p style="text-align: center;
                                font-size: medium;
                                margin-top: auto;"><b>${job.title}</b></p>
                        </div>
                        <div style="margin-left: 10px; margin-right: 10px">
                            <b>Price: </b>${job.price} <br>
                            <b>Publish date: </b>${job.publishTime} <br>
                            <b>Deadline: </b>${job.deadline} <br>
                        </div>
                        <form action="${root}/jobs/options" method="get" style="margin-top: 10px; float: bottom">
                            <div align="center">
                                <input type="hidden" name="jobId" value="${job.id}"/>
                                <button class="button_example" type="submit">See details</button>
                            </div>
                        </form>
                    </div>
                </c:forEach>
            </div>
        </div>

        <%--include footer--%>
        <%@ include file="/view/public/common/footer.jsp" %>
    </div>
</body>
</html>
