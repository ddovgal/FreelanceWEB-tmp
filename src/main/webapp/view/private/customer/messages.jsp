<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="root" value="${pageContext.request.contextPath}" />
<%@ page contentType="text/html;charset=UTF-8"%>
<head>
  <title>My messages</title>
  <script src="${root}/res/js/JQuery-1.10.2.js"  type="text/javascript"></script>
  <script src="${root}/res/js/jquery.jscrollpane.min.js"  type="text/javascript"></script>
  <link href="${root}/res/form.css" rel="stylesheet" type="text/css" media="screen" />
  <link href="${root}/res/style.css" rel="stylesheet" type="text/css" media="screen" />
  <link href="${root}/res/message-item.css" rel="stylesheet" type="text/css" media="screen" />
  <link href="${root}/res/jquery.jscrollpane.css" rel="stylesheet" type="text/css" media="screen" />


  <script type="text/javascript" id="sourcecode">
    $(function()
    {
      $('.scroll-pane').jScrollPane();
    });
  </script>
  <script>
    function scrollDown(){
      var objDiv = document.getElementById("messages-block");
      objDiv.scrollTop = objDiv.scrollHeight;
      /*setTimeout(reload, 5000)*/
    }
    function reload(){
      location.reload()
    }
  </script>

</head>
<body onload="scrollDown()">

<div class="wrapper">

  <%--include header--%>
  <%@ include file="/view/public/common/header.jsp" %>

  <div class="content">
    <div class="left-bar" style="width: 82px;
                          margin-left: 40px;
                          margin-right: 20px;">
      <div class="jspContainer" style="width: 85px">
        <div class="overflow-div-interlocutors" style="padding-right: 5px; width: 102px">
          <c:forEach items="${interlocutors}" var="interlocutor">
            <table>
              <tr valign="middle" align="center">
                <a href="${root}/messages/show_for/${interlocutor.id}">
                  <img id="userLogo" src="${root}/user/image/${interlocutor.id}" style="width: 75px;
              height: 75px;
              border: solid 2px #4b4b4b;
              border-radius: 5px 5px 5px 5px;
              margin-bottom: 7px"/>
                </a>
              </tr>
            </table>

          </c:forEach>
        </div>
      </div>
    </div>
    <div class="central-bar" style="width: 700px;
    margin-left: 20px;
    margin-right: 40px">
      <c:if test="${!isFromRefresh}">
        <div style="margin-left: -250px;
        margin-top: 290px; font-size: 50px;
        transform: rotate(90deg); float: left" align="center">Chose your interlocutor</div>
      </c:if>
      <c:if test="${isFromRefresh}">
        <div class="jspContainer">
          <div class="overflow-div-messages" id="messages-block">
            <c:forEach items="${messages}" var="message">

              <c:if test="${message.sender.id!=myId}">
                <li class="entry">
                  <img class="avatar" src="${root}/user/image/${message.sender.id}" style="width: 70px;
                height: 70px;
                /*border-radius: 3px 3px 3px 3px*/"/>
                  <p class="message" style="margin-left: 2px">
                      ${message.text}
                    <time class="timestamp">${message.time}</time>
                  </p>
                </li>
              </c:if>

              <c:if test="${message.sender.id==myId}">
                <li class="entry entry_mine">
                  <img class="avatar" src="${root}/user/image/${myId}" style="width: 70px;
                height: 70px;
                /*border-radius: 3px 3px 3px 3px*/"/>
                  <p class="message" style="margin-right: 2px">
                      ${message.text}
                    <time class="timestamp">${message.time}</time>
                  </p>
                </li>
              </c:if>

            </c:forEach>
          </div>
        </div>

        <textarea name="new_message" id="new_message" placeholder="Enter your message here" style="width: 700px;
        max-width: 700px;
        min-height: 80px;
        font-size: larger"></textarea>

        <div align="center" style="margin-top: 10px; margin-bottom: 10px">

          <script>
            function sendIt(myId, receiverId, text) {
              $.ajax({
                type: 'PUT',
                url: '${pageContext.request.contextPath}/messages/send',
                data: { myId: myId,
                  receiverId: receiverId,
                  text: text}
              });
            }
            function sendItPressing() {
              sendIt(${myId}, ${interlocutorId}, document.getElementById("new_message").value);
              location.reload()
            }
          </script>
          <button class="button_blue" type="submit" onclick="sendItPressing()" style="margin-right: 5px">Message it</button>
          <button class="button_blue" type="submit" onclick="location.reload()" style="margin-left: 5px">Reload page</button>
        </div>
      </c:if>
    </div>
    <%--<div class="right-bar">

    </div>--%>
  </div>

  <%--include footer--%>
  <%@ include file="/view/public/common/footer.jsp" %>
</div>


</body>
</html>