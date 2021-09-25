<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Update User</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>

<body>
<%@include file="/jsp/pageHead.jsp"%>
<h2>Update user</h2><br>
<form action="update.user" method="post">
    <label for="login">Login:</label><br>
    <input type="text" id="login" name="login" value="${user.login}"><br>
    <input type="hidden" id="login" name="userLogin" value="${user.login}">

    <label for="password">Password:</label><br>
    <input type="text" id="password" name="password" value="${user.password}"><br>

    Current role is: ${user.role}<br>
    <label for="roles">Select new role:</label>
    <select id="roles" name="role">
        <option value="1">admin</option>
        <option value="2" selected="selected">user</option>
    </select>
    <input type="submit" value="Submit">
</form>
</body>
</html>