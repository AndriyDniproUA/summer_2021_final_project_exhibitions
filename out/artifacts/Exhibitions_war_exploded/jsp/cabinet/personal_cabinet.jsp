<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Personal cabinet</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <script src="js/validateForms.js"></script>
</head>

<body>
<%@include file="/jsp/pageHead.jsp" %>

<h2>You may update your personal information below:</h2><br>
<form action="personal.cabinet" method="post" onsubmit="return validateRegistrationForm()">
    <table class="styled-table">
        <tr>
            <td>
                <label for="login">Login:</label><br>
            </td>
            <td>
                <input type="text" id="login" name="login" value="${currentUser.login}"><br>
                <input type="hidden" id="userId" name="id" value="${currentUser.id}"><br>
            </td>
        </tr>
        <tr>
            <td>
                Your role is:
            </td>
            <td>
                ${currentUser.role}<br>
            </td>
        </tr>
        <tr>
            <td>
                <label for="password">Password:</label>
            </td>
            <td>
                <input type="password" id="password" name="password" value="${currentUser.password}"><br>
            </td>
        </tr>
        <tr>
            <td>
                <label for="password2">Confirm password:</label>
            </td>
            <td>
                <input type="password" id="password2" name="password2" value="${currentUser.password}"><br>
            </td>
        </tr>
        <tr>
            <td>
                Deposit your account:
            </td>
            <td>
                <select id="deposit" name="deposit">
                    <option value="0" selected="selected">0</option>
                    <option value="100">100</option>
                    <option value="200">200</option>
                    <option value="500">500</option>
                    <option value="1000">1000</option>
                </select>
            </td>
        </tr>

        <tr><td></td><td></td></tr>
        <tr>
            <td>
            </td>
            <td>
                <input type="submit" value="Submit">
            </td>
        </tr>
    </table>
</form>
</body>
</html>