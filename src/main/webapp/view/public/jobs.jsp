<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="root" value="${pageContext.request.contextPath}" />
<head>
    <title>Jobs page</title>
    <link href="${root}/res/filter-form-style.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="${root}/res/style.css" rel="stylesheet" type="text/css" media="screen" />

</head>
<body>
<div class="wrapper">

    <%--include header--%>
    <%@ include file="/view/public/common/header.jsp" %>

    <div class="content">
        <div id="leftBar">
            <form>
                <div class="searchText">
                    <p>Title</p>
                    <p>Tags</p>
                    <p>Price</p>
                </div>
                <div class="searchInput">
                    <p><input id="searchTitle" type="text" style="width: 120px" placeholder="title" name="title"></p>
                    <p><input id="searchTags" type="text" style="width: 120px" placeholder="tag1 tag2 .." name="tags"></p>
                    <p class="smallText">
                        min<input id="searchMinRating" class="price" type="text" name="minPrice">
                        max<input id="searchMaxRating" class="price" type="text" name="maxPrice">
                    </p>
                </div>
                <button id="submitSearch" class="simple-button" type="submit" ><p>Find</p></button>
            </form>
        </div>
        <div id="centralBar">
            <c:forEach items="${jobs}" var="job">
                <div class="jobItem">

                </div>
                <td>${object.name} </td>
            </c:forEach>
        </div>
        <div id="rightBar" style="float: right; width: 200px; ">
            right bar
        </div>
    </div>

    <%--include footer--%>
    <%@ include file="/view/public/common/footer.jsp" %>
</div>


</body>
</html>