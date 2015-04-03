
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Developer profile</title>
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
                        <div style="background: #000000; color: white">
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
                <div id="pofileData">
                    <h2>${developerUser.snf}</h2>
                    <p><b>Email: </b>${developerUser.email}</p>
                    <p><b>DOB: </b>${developerUser.birthday}</p>
                    <p><b>Rating: </b>${developerUser.rating}</p><hr style="color: #2a78f6; ">
                    <p><b>Skills: </b>${developerUser.skills}</p>
                </div>
            </div>
            <div class="right-bar">
                <h4>Current jobs</h4>
                <c:forEach items="${currentJobs}" var="job">
                    <div class="jobItem" style="font-size: smaller; margin-left: 8px; width: 200px;">
                        <div style="background: forestgreen; color: white">
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
