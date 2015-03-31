<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
  <title>Register Job</title>

  <link href="${pageContext.request.contextPath}/res/form.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>
<div class="wrapper">

  <%--include header--%>
  <%@ include file="/view/public/common/header.jsp" %>

  <div class="content">
    <h1 class="pageTitle">Job register form</h1>
    <div class="container">
      <div  class="form">
        <c:if test="${not empty error}">
          Error: ${error}
        </c:if>
        <form id="signup-form" action="${pageContext.request.contextPath}/jobs/register" method="post">

          <p class="contact"><label for="title">Title</label></p>
          <input id="title" name="title" placeholder="title" required="" tabindex="1" type="text">

          <p class="contact"><label for="description">Description</label></p>
          <input id="description" name="description" placeholder="description" required="" type="text" height="5" >

          <p class="contact"><label for="price">Price</label></p>
          <input type="price" id="price" name="price" required="">

          <p class="contact"><label for="agreement">Agreement</label></p>
          <input type="agreement" id="agreement" name="agreement" required="">

          <p class="contact"><label for="tags">Agreement</label></p>
          <input type="tags" id="tags" name="tags" required="">

          <p class="contact"><label for="deadline">Deadline in (dd/MM/yyyy) format</label></p>
          <input type="text" id="deadline" name="deadline" required="">
          <input type="hidden" value="<sec:authentication property="principal.email" />" name="customerId" />
"
          <input class="button" name="submit" id="submit" tabindex="5" value="Register job!" type="submit">
        </form>
      </div>
    </div>
  </div>

  <%--include footer--%>
  <%@ include file="/view/public/common/footer.jsp" %>
</div>

</body>
</html>
