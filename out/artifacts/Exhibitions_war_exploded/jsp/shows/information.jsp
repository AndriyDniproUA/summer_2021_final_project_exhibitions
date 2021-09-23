<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ page isELIgnored="false" %>--%>


<html>
<head>
    <title>Information</title>
</head>
<body>
<h2>INFORMATION</h2><br>
<h3>${message}</h3>

</body>
<form action="display.shows">
    <input type="submit" value="Shows" />
</form><br>

<form action="login">
    <input type="submit" value="Login" />
</form><br>

<form action="logout">
    <input type="submit" value="Logout" />
</form><br>

</html>
