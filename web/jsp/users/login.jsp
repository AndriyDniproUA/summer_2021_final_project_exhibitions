<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
%>

<html>
<head>
    <title>Login Page</title>
    <script src="js/validateForms.js"></script>
</head>

<body>

<h2>Please login</h2><br>

<form action="login" method="post"  onsubmit="return validateLoginForm()">

    <label for="login">Login:</label><br>
    <input type="text" id="login" name="login" value="nick"><br>
    <label for="password">Password:</label><br>
    <input type="password" id="password" name="password" value="111">
    <input type="submit" value="Submit">

</form>
<br>
<%--<a href="register">Register</a>--%>

<form action="register">
    <input type="submit" value="Register" />
</form><br>

</body>
</html>




