<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<link href="${root}/res/style.css" rel="stylesheet" type="text/css" media="screen" />
<nav class = "nav-primary" role = "navigation">
  <ul role = "menubar">
    <li role = "presentation"><a href = "${root}/home" role = "menuitem">Service</a></li>
    <li role = "presentation"><a href = "${root}/view/public/developer_register.jsp" role = "menuitem">Developer</a></li>
    <li role = "presentation"><a href = "#" role = "menuitem">Admin</a></li>
    <li role = "presentation"><a href = "#" role = "menuitem">Customer</a></li>
    <li role = "presentation"><a href = "#" role = "menuitem">About us</a></li>
  </ul>
</nav>


<style>
    #primary_nav_wrap
    {
        margin-top:15px
    }

    #primary_nav_wrap ul
    {
        list-style:none;
        position:relative;
        float:left;
        margin:0;
        padding:0
    }

    #primary_nav_wrap ul a
    {
        display:block;
        color:#333;
        text-decoration:none;
        font-weight:700;
        font-size:12px;
        line-height:32px;
        padding:0 15px;
        font-family:"HelveticaNeue","Helvetica Neue",Helvetica,Arial,sans-serif
    }

    #primary_nav_wrap ul li
    {
        position:relative;
        float:left;
        margin:0;
        padding:0
    }

    #primary_nav_wrap ul li.current-menu-item
    {
        background:#ddd
    }

    #primary_nav_wrap ul li:hover
    {
        background:#f6f6f6
    }

    #primary_nav_wrap ul ul
    {
        display:none;
        position:absolute;
        top:100%;
        left:0;
        background:#fff;
        padding:0
    }

    #primary_nav_wrap ul ul li
    {
        float:none;
        width:200px
    }

    #primary_nav_wrap ul ul a
    {
        line-height:120%;
        padding:10px 15px
    }

    #primary_nav_wrap ul ul ul
    {
        top:0;
        left:100%
    }

    #primary_nav_wrap ul li:hover > ul
    {
        display:block
    }
</style>
<nav id="primary_nav_wrap">
    <ul>
        <li><a href="#">Sign up</a>
            <ul>
                <li><a href="${root}/view/public/developer_register.jsp">Developer</a></li>
                <li><a href="${root}/view/public/customer_register.jsp">Customer</a></li>
                <li><a href="${root}/view/public/admin_register.jsp">Admin</a></li>
            </ul>
        </li>
    </ul>
</nav>
