<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Register Developer</title>

    <link href="${pageContext.request.contextPath}/res/form.css" rel="stylesheet" type="text/css" media="screen" />
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
            <h1 class="pageTitle">Developer sign up form</h1>
            <div class="container">
                <div  class="form">
                    <c:if test="${not empty error}">
                        Error: ${error}
                    </c:if>
                    <form id="developer-signup-form" action="${pageContext.request.contextPath}/usr/developer/register" method="post">

                        <p class="contact"><label for="snf">S N F</label></p>
                        <input id="snf" name="snf" placeholder="First and last name" required="" tabindex="1" type="text">

                        <p class="contact"><label for="email">Email</label></p>
                        <input id="email" name="email" placeholder="example@domain.com" required="" type="email">

                        <p class="contact"><label for="password">Create a password</label></p>
                        <input type="password" id="password" onchange="checkPasswords()" name="password" required="">

                        <p class="contact"><label for="repassword">Confirm your password</label></p>
                        <input type="password" id="repassword" onchange="checkPasswords()" name="repassword" required="">

                        <p class="contact"><label for="skills">Skills</label></p>
                        <input id="skills" name="skills" required="" type="text">

                        <p class="contact"><label for="birthday">DOB in (dd/MM/yyyy) format</label></p>
                        <input type="text" id="birthday" name="birthday" required="">
                        <input class="button" name="submit" id="submit" tabindex="5" value="Sign me up!" type="submit">
                    </form>
                </div>
            </div>

        </div>

        <%--include footer--%>
        <%@ include file="/view/public/common/footer.jsp" %>
    </div>

</body>
</html>
