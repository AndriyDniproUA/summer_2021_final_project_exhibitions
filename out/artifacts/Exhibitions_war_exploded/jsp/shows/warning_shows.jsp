<%@include file="/jspf/head.jspf" %>

<html>
<head>
    <title>Warning</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<%@include file="/jsp/pageHead.jsp"%>
<h2><fmt:message key='warning_shows_jsp.label.warning'/>!</h2><br>

<div class="warning">
<c:forEach var="message" items="${messageList}">
    <h3>${message}</h3><br>
</c:forEach>

<h3>${message}</h3>
</div>

</body>


</html>


