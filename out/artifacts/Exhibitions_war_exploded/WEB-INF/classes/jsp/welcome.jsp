<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <title>Welcome</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<%@include file="/jsp/pageHead.jsp"%>

<h2>Welcome ${currentUser.login}</h2><br>
Your current role is: <c:out value="${currentUser.role}"/><br><br>

<%--<a href="index.html">Go to the homepage</a>--%>

<%--<form action="index.html">--%>
<%--    <input type="submit" value="Home" />--%>
<%--</form>--%>

<%--<form action="display.shows">--%>
<%--    <input type="submit" value="Shows" />--%>
<%--</form>--%>
</body>
</html>
