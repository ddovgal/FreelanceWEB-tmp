<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="root" value="${pageContext.request.contextPath}" />
<head>
    <title>home page</title>
</head>
<body>
    <%--include header--%>
    <%@ include file="/view/public/common/header.jsp" %>

    <div class="content" >
        <div class="leftBar">
        </div>
        <div class="centralBar">

        </div>
        <div class="rightBar">
        </div>
    </div>

    <%--include footer--%>
    <%@ include file="/view/public/common/footer.jsp" %>
</body>
</html>
