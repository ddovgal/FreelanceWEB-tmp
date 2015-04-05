<%@ page import="com.project.businesslogic.user.User" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="root" value="${pageContext.request.contextPath}" />
<head>
  <title>Profile edit</title>
  <link href="${root}/res/filter-form-style.css" rel="stylesheet" type="text/css" media="screen" />
  <link href="${root}/res/style.css" rel="stylesheet" type="text/css" media="screen" />
  <link href="${root}/res/item-style.css" rel="stylesheet" type="text/css" media="screen" />
  <link href="${root}/res/form.css" rel="stylesheet" type="text/css" media="screen" />

  <script>
    function checkPasswords() {
      var pass = document.getElementById("password");
      var passconfirm = document.getElementById("repassword");
      var button = document.getElementById("signup-form");
      if (pass.value && passconfirm.value) {
        if (pass.value == passconfirm.value) {
          pass.setAttribute("style", "border-width: 1px; border-style: solid; border-color: #00FF00");
          passconfirm.setAttribute("style", "border-width: 1px; border-style: solid; border-color: #00FF00");
          button.removeAttribute("disabled");
        } else {
          pass.setAttribute("style", "border-width: 1px; border-style: solid; border-color: #FF0000");
          passconfirm.setAttribute("style", "border-width: 1px; border-style: solid; border-color: #FF0000");
          button.setAttribute("disabled", "true");
        }
      } else {
        pass.setAttribute("style", "border-width: 1px; border-style: dotted; border-color: #000000");
        passconfirm.setAttribute("style", "border-width: 1px; border-style: dotted; border-color: #000000");
      }
    }
  </script>
</head>
<body>
<div class="wrapper">

  <%--include header--%>
  <%@ include file="/view/public/common/header.jsp" %>

  <div class="content">
    <div class="left-bar">

    </div>
    <div class="central-bar">
      <div class="content">
        <h1 class="pageTitle">Edit your profile</h1>
        <div class="container">
          <div  class="form">
            <c:if test="${not empty error}">
              Error: ${error}
            </c:if>
            <form id="signup-form" action="${root}/usr/developer/edit" method="post" enctype="multipart/form-data">

              <p class="contact"><label for="snf">Surname Name Fathersname</label></p>
              <input id="snf" style="margin-top: 5px" name="snf" value="${user.snf}" required="" tabindex="1" type="text">

              <p class="contact"><label for="password">Enter new password</label></p>
              <input type="password" style="margin-top: 5px" id="password" onchange="checkPasswords()" name="password" required="">

              <p class="contact"><label for="repassword">Confirm your password</label></p>
              <input type="password" style="margin-top: 5px" id="repassword" onchange="checkPasswords()" name="repassword" required="">

              <p class="contact"><label for="skills">Skills</label></p>
              <input id="skills" name="skills" value="${user.skills}" required="" type="text">

              <%  User user = (User) request.getAttribute("user");
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String newFormatDate = sdf.format(user.getBirthday());
              %>

              <p class="contact"><label for="birthday">Your birthday in (dd/MM/yyyy) format</label></p>
              <input type="text" style="margin-top: 5px" id="birthday" name="birthday" value="<%=newFormatDate%>" required="">

              <p class="contact" style="margin-bottom: 5px"><label for="userImage">Your avatar image</label></p>
              <input type="file" id="userImage" name="userImage" accept=".jpeg, .jpg">

              <div align="center" style="margin-top: 10px">
                <input class="button" name="submit" id="submit" tabindex="5" value="Confirm changes" type="submit">
                <input type="hidden" value="${user.id}" name="lastId" />
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
    <div class="right-bar">

    </div>
  </div>

  <%--include footer--%>
  <%@ include file="/view/public/common/footer.jsp" %>
</div>


</body>
</html>