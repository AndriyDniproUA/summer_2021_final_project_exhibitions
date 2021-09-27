<%@include file="/jspf/head.jspf" %>

<html>
<head>
    <title>Show users</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<%@include file="/jsp/pageHead.jsp" %>
<h2><fmt:message key='users_jsp.label.user_list'/></h2>
<table class="styled-table">
    <tr>
        <th><fmt:message key='users_jsp.label.item_number'/></th>
        <th><fmt:message key='users_jsp.label.login'/></th>
        <th><fmt:message key='users_jsp.label.role'/></th>
        <th><fmt:message key='users_jsp.label.balance'/></th>
        <th><fmt:message key='users_jsp.label.delete'/></th>
        <th><fmt:message key='users_jsp.label.update'/></th>

    </tr>
    <c:forEach var="user" items="${users}" varStatus="theCount">
        <tr>
            <td><c:out value="${theCount.count}"/></td>
            <td><c:out value="${user.login}"/></td>
            <td><c:out value="${user.role}"/></td>
            <td><c:out value="${user.balance}"/></td>
            <td><a href="delete.user?id=${user.id}"><fmt:message key='users_jsp.link.delete'/></a></td>
            <td><a href="update.user?id=${user.id}"><fmt:message key='users_jsp.link.update'/></a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>


