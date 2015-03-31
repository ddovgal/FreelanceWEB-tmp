<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="root" value="${pageContext.request.contextPath}" />
<head>
    <title>Jobs page</title>
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
            <div id="jobs-filter-form-container">
                <form>
                    <table style="color: #000000">
                        <tr>
                            <td>Title:</td><td><input id="searchTitle" type="text" style="width: 120px" placeholder="title" name="title"></td>
                        </tr>
                        <tr>
                            <td>Tags:</td><td><input id="searchTags" type="text" style="width: 120px" placeholder="tag1 tag2 .." name="tags"></td>
                        </tr>
                        <tr>
                            <td>Price:</td><td style="font-size: smaller">min<input id="searchMinRating" class="price" type="text" name="priceMin">
                                              max<input id="searchMaxRating" class="price" type="text" name="priceMax"></td>
                        </tr>
                        <tr>
                            <td><button id="submitSearch"  class="button_example" type="submit" >Find</button></td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
        <div class="central-bar">
            <c:forEach items="${jobs}" var="job">
                <div class="jobItem" style="font-size: smaller">
                    <div style="background: #000000; color: white">
                        <p style="text-align: center;
                                font-size: medium;
                                margin-top: auto;">${job.title}</p>
                    </div>
                    Price: ${job.price} <br>
                    Publish date: ${job.publishTime} <br>
                    Deadline: ${job.deadline} <br>
                    Tags: ${job.tags} <br><hr>
                    Description: ${job.description}
                </div>
                <form action="${root}/jobs/byId" method="get" >
                    <input type="hidden" name="jobId" value="${job.id}"/>
                    <button class="button_example" type="submit">Open</button>
                </form>
            </c:forEach>
        </div>
        <div class="right-bar">

        </div>
    </div>

    <%--include footer--%>
    <%@ include file="/view/public/common/footer.jsp" %>
</div>


</body>
</html>