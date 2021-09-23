<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <title>Show users</title>
    <link rel="stylesheet" type ="text/css" href="css/styles.css">
</head>
<body>
<%@include file="/jsp/pageHead.jsp"%>
<h2>User List</h2>
<table class="styled-table">
    <tr>
    <th>No.</th>
    <th>Login</th>
    <th>Role</th>
        <c:if test="${currentUser.role eq 'admin'}">
            <th>Delete</th>
            <th>Update</th>
        </c:if>
    </tr>
    <c:forEach var="user" items="${users}" varStatus="theCount">
        <tr>
            <td><c:out value="${theCount.count}"/></td>
            <td><c:out value="${user.login}"/></td>
            <td><c:out value="${user.role}"/></td>

            <c:if test="${currentUser.role eq 'admin'}">
            <td><a href="delete.user?login=${user.login}">delete</a></td>
            <td><a href="update.user?login=${user.login}">update</a></td>
            </c:if>
        </tr>
    </c:forEach>
</table>


<%--<br/>--%>
<%--<a href="login">Go to login page</a><br>--%>
<%--<a href="logout">Logout</a>--%>
</body>
</html>
