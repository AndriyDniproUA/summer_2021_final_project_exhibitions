<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ page isELIgnored="false" %>--%>


<html>
<head>
    <title>Warning</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<h2>WARNING!</h2><br>

<div class="warning">
<c:forEach var="message" items="${messageList}">
    <h3>${message}</h3><br>
</c:forEach>

<h3>${message}</h3>
</div>

<a href="display.shows">See all shows</a><br>
<a href="login">Login</a>

</body>


</html>
