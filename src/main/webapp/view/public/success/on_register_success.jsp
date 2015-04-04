<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="root" value="${pageContext.request.contextPath}" />
<head>
    <title>${titleMessage}</title>
</head>
<body>
<div class="wrapper">

    <%--include header--%>
    <%@ include file="/view/public/common/header.jsp" %>

    <div class="content">
        <h1 class="pageTitle">${successMessage}</h1>
        <p></p>
    </div>

    <%--include footer--%>
    <%@ include file="/view/public/common/footer.jsp" %>
</div>


</body>
</html>
