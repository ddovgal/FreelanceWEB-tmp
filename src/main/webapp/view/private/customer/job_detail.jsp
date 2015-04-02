<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="root" value="${pageContext.request.contextPath}" />
<head>
  <title>Job page</title>
  <link href="${root}/res/filter-form-style.css" rel="stylesheet" type="text/css" media="screen" />
  <link href="${root}/res/style.css" rel="stylesheet" type="text/css" media="screen" />
  <link href="${root}/res/item-style.css" rel="stylesheet" type="text/css" media="screen" />

</head>
<body>
<div class="wrapper">

  <%--include header--%>
  <%@ include file="/view/public/common/header.jsp" %>

  <div class="content">
    <div class="left-bar">

    </div>
    <div class="central-bar">
      Job Detail
    </div>
    <div class="right-bar">

    </div>
  </div>

  <%--include footer--%>
  <%@ include file="/view/public/common/footer.jsp" %>
</div>


</body>
</html>