<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="root" value="${pageContext.request.contextPath}" />
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer profile</title>
    <link href="${root}/res/item-style.css" rel="stylesheet" type="text/css" media="screen" />

</head>
<body>
    <div class="wrapper">

        <%--include header--%>
        <%@ include file="/view/public/common/header.jsp" %>

        <div class="content">
            <div id="leftBar">
                <form id="buton-form" action="${root}/jobs/newJob" method="post">
                    <input class="button" name="submit" value="Create new job" type="submit">
                </form>
            </div>
            <div id="centralBar" class="customer-central-bar">
                <c:forEach items="${jobs}" var="job">
                    <div class="jobItem">

                    </div>
                    <td>${object.name} </td>
                </c:forEach>
            </div>
            <div id="rightBar" style="float: right; width: 200px; ">

            </div>
        </div>

        <%--include footer--%>
        <%@ include file="/view/public/common/footer.jsp" %>
    </div>
</body>
</html>
