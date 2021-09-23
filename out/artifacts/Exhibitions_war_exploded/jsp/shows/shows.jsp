<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <title>Display shows</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<%@include file="/jsp/pageHead.jsp"%>
<h3>Shows List</h3>
<table class="styled-table">
    <tr>
        <th>No.</th>
        <th>Subject</th>
        <th>Begins</th>
        <th>Ends</th>
        <th>Opens</th>
        <th>Closes</th>
        <th>Ticket price</th>
        <th>Rooms</th>
        <c:if test="${currentUser.role eq 'admin'}">
            <th>Delete</th>
        </c:if>
    </tr>
    <c:forEach var="show" items="${shows}" varStatus="theCount">
        <tr>
            <td>${theCount.count}</td>
            <td>${show.subject}</td>
            <td>${show.dateBegins}</td>
            <td>${show.dateEnds}</td>
            <td>${show.timeOpens}</td>
            <td>${show.timeCloses}</td>
            <td>${show.price}</td>
            <td><c:forEach var="room" items="${show.rooms}">
                ${room}
            </c:forEach></td>


            <c:if test="${currentUser.role eq 'admin'}">
                <td><a href="delete.show?id=${show.id}">delete</a></td>
            </c:if>
        </tr>
    </c:forEach>
</table>
<br/>


<form action="display.shows" method="post">

    Choose ordering of the show list:
        <input type="radio" id="bySubject" name="orderBy" value="bySubject">
        <label for="bySubject">by subject</label>

        <input type="radio" id="byDate" name="orderBy" value="byDate">
        <label for="byDate">by date</label>

        <input type="radio" id="byPrice" name="orderBy" value="byPrice">
        <label for="byPrice">by price</label><br><br>

        <label for="date_begins">Start date:</label>
        <input type="date" id="date_begins" name="date_begins">
        <label for="date_ends">End date:</label>
        <input type="date" id="date_ends" name="date_ends">

        <input type="submit" value="Submit"/>
</form>


<%--<c:if test="${currentUser.role eq 'admin'}">--%>
<%--    &lt;%&ndash;    <a href="add.show">Add show</a><br><br>&ndash;%&gt;--%>
<%--    <form action="add.show">--%>
<%--        <input type="submit" value="Add new show"/>--%>
<%--    </form>--%>
<%--    <br>--%>
<%--</c:if>--%>

<%--<form action="login">--%>
<%--    <input type="submit" value="Login"/>--%>
<%--</form>--%>
<%--<br>--%>

<%--<form action="logout">--%>
<%--    <input type="submit" value="Logout"/>--%>
<%--</form>--%>
<%--<br>--%>


<%--<a href="login">Go to login page</a><br><br>--%>
<%--<a href="logout">Logout</a>--%>
</body>
</html>
