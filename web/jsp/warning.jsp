<html>
<head>
    <title>Warning</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<%@include file="/jsp/pageHead.jsp"%>
<h2>WARNING!</h2><br>

<div class="warning">
<c:forEach var="message" items="${messageList}">
    <h3>${message}</h3><br>
</c:forEach>

<h3>${message}</h3>

</div>
</body>
</html>
