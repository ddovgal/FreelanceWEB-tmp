<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="root" value="${pageContext.request.contextPath}" />
<head>
  <title>About us</title>
  <link href="${root}/res/filter-form-style.css" rel="stylesheet" type="text/css" media="screen" />
  <link href="${root}/res/style.css" rel="stylesheet" type="text/css" media="screen" />
  <link href="${root}/res/item-style.css" rel="stylesheet" type="text/css" media="screen" />
  <link href="${root}/res/about_us_cool_style.css" rel="stylesheet" type="text/css" media="screen" />

</head>
<body>
<div class="wrapper">

  <%--include header--%>
  <%@ include file="/view/public/common/header.jsp" %>

  <div class="content">
    <div class="grid page-section" style="margin-left: 0px; margin-right: 0px; padding-top: 0px">
      <%--<div class="node">
        <h1 class="heading heading-uber-leading heading-collapse-whole-line heading-centre">About Us</h1>//TODO: change
        <div class="division-bottom"></div>
        <div class="text-span2">
          <p>It is our term paper on design of the software. Her founders - Dovgal Dmitry and Grigory Rozhkov, who agreed to make this term paper for Eugen Seriy. So for all founders of this Free Coin freelance system - Dovgal Dmitry and Eugen Seriy.</p>
          <p>The project was created about a week or one and a half. First Dovgal Dmitry had a Windows application, ready for 80%, but Grisha overpersuaded him to make this project in WEB. So, knowing nothing neither about servlets, nor about JSP, especially about Spring MVC, Dmitry was accepted to a project redoing, to be exact creation anew, thus studying all these new technologies.</p>
          <p>But this stage we have that we have. Quite quite good freelance site. We put in it heart and soul so, we hope, it will be pleasant to you.</p>
          <p>I express huge gratitude to Grisha that helped me all this time, I told as as, I answered all silly questions and I trained me in cool pieces and technologies.</p>
        </div>
      </div>--%>
    </div>
    <section class="grid circle-grid team" style="margin-left: 0px; margin-right: 0px; padding-bottom: 0px; padding-top: 0px">
      <div class="node">
        <h1 class="heading heading-uber-leading heading-collapse-whole-line heading-centre">The Team</h1>
        <div class="division-bottom"></div>
      </div>
      <div class="grid">
        <article class="item">
          <div class="node">
            <div class="circular-image" style="height: 296px">
              <img src="${root}/user/image/-1" alt="Dmitriy Dovgal">
            </div>
            <hgroup>
              <h1 class="heading heading-small heading-collapse-line">Dmitriy Dovgal</h1>
              <h2 class="heading heading-extra-small heading-collapse-line">Assistant to the chief developer</h2>
            </hgroup>
            <p>I studied and in parallel I developed about, what I am now glad to report.</p>
          </div>
        </article><!----><%--<article class="item">//TODO: change
        <div class="node">
          <div class="circular-image" style="height: 296px">
            <img src="${root}/user/image/-2" alt="Grigoriy Rozhkov">
          </div>
          <hgroup>
            <h1 class="heading heading-small heading-collapse-line">Grigoriy Rozhkov</h1>
            <h2 class="heading heading-extra-small heading-collapse-line">Main developer</h2>
          </hgroup>
          <p>Chapter of the project, main on a backend and simply the guy who knows much about cool and interesting things.</p>
        </div>
      </article>--%><article class="item">
        <div class="node">
          <div class="circular-image" style="height: 296px">
            <img src="${root}/user/image/-2" alt="Anonymous">
          </div>
          <hgroup>
            <h1 class="heading heading-small heading-collapse-line">Anonymous</h1>
            <h2 class="heading heading-extra-small heading-collapse-line">Anon person</h2>
          </hgroup>
          <p>Nobody knows him, nobody seen him, he's like a ghost, a shadow.</p>
        </div>
      </article><!----><%--<article class="item">
        <div class="node">
          <div class="circular-image" style="height: 296px">
            <img src="${root}/user/image/-3" alt="Eugen Serity">
          </div>
          <hgroup>
            <h1 class="heading heading-small heading-collapse-line">Eugen Seriy</h1>
            <h2 class="heading heading-extra-small heading-collapse-line">Customer of the project, idler</h2>
          </hgroup>
          <p>Knows nothing, made nothing, paid 600 UAH. Fool short.</p>
        </div>
      </article>--%><article class="item">
        <div class="node">
          <div class="circular-image" style="height: 296px">
            <img src="${root}/user/image/-3" alt="Eugen Serity">
          </div>
          <hgroup>
            <h1 class="heading heading-small heading-collapse-line">Eugen Seriy</h1>
            <h2 class="heading heading-extra-small heading-collapse-line">Wery cunning guy :D</h2>
          </hgroup>
          <p>Work very very hard. Nothing more to say.</p>
        </div>
      </article>
      </div>
    </section>
  </div>

  <%--include footer--%>
  <%@ include file="/view/public/common/footer.jsp" %>
</div>


</body>
</html>