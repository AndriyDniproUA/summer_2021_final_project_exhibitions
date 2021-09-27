
<html>
<head>
    <title>Show users</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<%@include file="/jsp/pageHead.jsp" %>
<h2>User List</h2>
<table class="styled-table">
    <tr>
        <th>No.</th>
        <th>Login</th>
        <th>Role</th>
        <th>Balance</th>
        <th>Delete</th>
        <th>Update</th>

    </tr>
    <c:forEach var="user" items="${users}" varStatus="theCount">
        <tr>
            <td><c:out value="${theCount.count}"/></td>
            <td><c:out value="${user.login}"/></td>
            <td><c:out value="${user.role}"/></td>
            <td><c:out value="${user.balance}"/></td>
            <td><a href="delete.user?id=${user.id}">delete</a></td>
            <td><a href="update.user?id=${user.id}">update</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
