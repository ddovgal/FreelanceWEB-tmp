<%--
  Created by IntelliJ IDEA.
  User: grigoriy
  Date: 27.03.15
  Time: 11:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign in page</title>
    <link href="${pageContext.request.contextPath}/res/form.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>
    <div class="wrapper">

        <%--include header--%>
        <%@ include file="/view/public/common/header.jsp" %>

        <div class="content" style="height: 400px">
            <h1 class="pageTitle">Sign in</h1>
            <div class="container">
                <div  class="form">
                    <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
                        <div class="errorblock">
                            Your login attempt was not successful, try again.<br/>
                                ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
                        </div>
                    </c:if>


                    <br>
                    <form name='f' action="<c:url value='/j_spring_security_check' />" method='POST'>

                        <p class="contact"><label for="email">Email</label></p>
                        <p><input id="email" type='text' name='username' style="border-width: 1px; border-style: solid"></p>
                        <p class="contact"><label for="password">Password</label></p>
                        <p><input id="password" type='password' name='password' style="border-width: 1px; border-style: solid"></p>
                        <p><input id="remember" name="_spring_security_remember_me" type="checkbox" /> Remember me</p>
                        <div style="margin-top: 5px" align="center">
                            <input class="button" name="submit" id="submit" tabindex="5" value="Sign in" type="submit">
                        </div>

                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}" />
                    </form>
                </div>
            </div>
        </div>

        <%--include footer--%>
        <%@ include file="/view/public/common/footer.jsp" %>
    </div>

</body>
</html>
