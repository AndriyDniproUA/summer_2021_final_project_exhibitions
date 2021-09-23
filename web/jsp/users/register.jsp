<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Registration</title>
    <script src="js/validateForms.js"></script>
</head>

<body>
<h2>Please register</h2><br>
<form action="register" method="post" onsubmit="return validateLoginForm()">
    <label for="login">Login:</label><br>
    <input type="text" id="login" name="login"><br>

    <label for="password">Password:</label><br>
    <input type="password" id="password" name="password">

    <input type="submit" value="Submit">
</form>
</body>
</html>