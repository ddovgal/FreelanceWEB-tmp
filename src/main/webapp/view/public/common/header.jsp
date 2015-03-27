<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<link href="${root}/res/style.css" rel="stylesheet" type="text/css" media="screen" />

<div class="top">

</div>

<ul class="navigation">
    <li><a href="${root}/home" title="Home">Home</a></li>
    <li><a href="" title="Jobs">Jobs</a></li>
    <li><a href="" title="About us">About us</a></li>
    <li><a href="" title="Contacts">Contacts</a></li>
    <li style="margin-left: 300px"><a href="" title="Sign up">Sign up</a>
        <ul>
            <li><a href="${root}/view/public/developer_register.jsp" title="Developer">Developer</a></li>
            <li><a href="${root}/view/public/customer_register.jsp" title="Customer">Customer</a></li>
            <li><a href="${root}/view/public/admin_register.jsp" title="Admin">Admin</a></li>
        </ul>
    </li>
    <li><a href="" title="Sign in">Sign in</a></li>
    <div class="clear"></div>
</ul>
