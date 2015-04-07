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
    <h1 class="pageTitle"><c:if test="${!isToCreate}">Job edit</c:if><c:if test="${isToCreate}">Job register</c:if></h1>
    <div class="container">
      <div  class="form">
        <c:if test="${not empty error}">
          Error: ${error}
        </c:if>
        <form id="signup-form" action="${pageContext.request.contextPath}/jobs/register" method="post">

          <p class="contact"><label for="title">Title</label></p>
          <input <c:if test="${!isToCreate}">value="${jobObj.title}"</c:if> id="title" name="title" required="" type="text">

          <p class="contact"><label for="tags">Tags (use ; to split)</label></p>
          <input <c:if test="${!isToCreate}">value="${jobObj.tags}"</c:if> type="text" id="tags" name="tags" required="">

          <p class="contact"><label for="description">Description</label></p>
          <textarea id="description" name="description" required="" rows="10" cols="60" style="max-width: 400px"><c:if test="${!isToCreate}">${jobObj.description}</c:if></textarea>

          <p class="contact"><label for="price">Price</label></p>
          <input <c:if test="${!isToCreate}">value="${jobObj.price}"</c:if> type="text" id="price" name="price" required="">

          <c:if test="${isToCreate}">
            <p class="contact"><label for="deadline">Deadline in (dd/MM/yyyy) format</label></p>
            <input type="text" id="deadline" name="deadline" required="">
          </c:if>

          <p class="contact"><label for="agreement">Agreement</label></p>
          <textarea type="agreement" id="agreement" name="agreement" required="" rows="10" cols="60" style="max-width: 400px"><c:if test="${!isToCreate}">${jobObj.agreement}</c:if></textarea>

          <div align="center"><input class="button" name="submit" id="submit" size="60" value="<c:if test="${!isToCreate}">Edit job</c:if><c:if test="${isToCreate}">Register job</c:if>" type="submit" style="margin-top: 10px"></div>

          <input type="hidden" value="${jobObj.id}" name="lastId" />
          <input type="hidden" value="${isToCreate}" name="isToCreate" />
          <input type="hidden" value="<sec:authentication property="principal.id" />" name="customerId" />
        </form>
      </div>
    </div>
  </div>

  <%--include footer--%>
  <%@ include file="/view/public/common/footer.jsp" %>
</div>

</body>
</html>
