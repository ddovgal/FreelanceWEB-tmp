<%@ page import="com.project.security.CustomUserDetails" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="root" value="${pageContext.request.contextPath}" />
<c:set var="isUserSessionOpened" value="<%=SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUserDetails %>"/>
<link href="${root}/res/style.css" rel="stylesheet" type="text/css" media="screen" />

<div class="top">
    <c:if test="${isUserSessionOpened}">
        <div id="profileHeader" style="
                          float: right;
                          background: powderblue;
                          display: inline-block;
                          border: 2px black solid;
                          border-radius: 5px 5px 5px 5px;
                          height: 50px;
                          width: 200px;
                          margin-top: 10px;
                          margin-right: 10px;
                          font-size: 14px;
                          color: black;">
            <div id="profileHeaderText" style="
                display: inline-block;
                width: 120px;
                padding-left: 10px;">
                <p id="lNameIconData" style="margin-top: 0px;
                                             margin-bottom: 0px;
                                             font-size: large;">
                    <a href="${root}/p-redirect" style="  color: navy;font-size: 14px;">
                        <sec:authentication property="principal.snf" />
                    </a>
                </p>
                <p id="emailIconData" style="margin-top: 0px;
                                             margin-bottom: 0px;">
                    <sec:authentication property="principal.email" /></p>
            </div>
            <button style="float: right;
                            border: darkblue 2px;
                            border-radius: 5px;
                            margin-top: 10px;
                            padding-top: 0px;
                            margin-right: 5px;
                            height: 30px;
                            background: #4b8df9;
                            color: #fbf7f7;
                            font-weight: bold;
                            text-shadow: 0 -1px 1px #222;
                            font-family:Verdana, Geneva, sans-serif;"
                    onclick="javascript:formSubmit()"><p>Logout</p></button>
            <c:url value="/j_spring_security_logout" var="logoutUrl" />
            <!-- csrt for log out-->
            <form id="logoutForm" action="${logoutUrl}" method="post" >
                <input type="hidden"
                       name="${_csrf.parameterName}"
                       value="${_csrf.token}" />
            </form>
            <script>
                function formSubmit() {
                    document.getElementById("logoutForm").submit();
                }
            </script>

        </div>
    </c:if>
</div>

<ul class="navigation">
    <li><a href="${root}/home" title="Home">Home</a></li>
    <li><a href="${root}/jobs/byCriterion" title="Jobs">Jobs</a></li>
    <li><a href="" title="About us">About us</a></li>
    <li><a href="" title="Contacts">Contacts</a></li>

    <c:if test="${!isUserSessionOpened}">
        <li style="margin-left: 600px"><a href="" title="Sign up">Sign up</a>
            <ul>
                <li><a href="${root}/view/public/developer_register.jsp" title="Developer">Developer</a></li>
                <li><a href="${root}/view/public/customer_register.jsp" title="Customer">Customer</a></li>
                <li><a href="${root}/view/public/admin_register.jsp" title="Admin">Admin</a></li>
            </ul>
        </li>
        <li><a href="${root}/p-redirect" title="Sign in">Sign in</a></li>
    </c:if>
    <%--<c:if test="${isUserSessionOpened}">--%>
        <%--<li style="margin-left: 400px"><a href="${root}/p-redirect" title="Sign out">Sign out</a></li>--%>
    <%--</c:if>--%>


    <div class="clear"></div>
</ul>
