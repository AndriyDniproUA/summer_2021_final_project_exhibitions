<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <title>Display shows</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<%@include file="/jsp/pageHead.jsp" %>
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
        <c:if test="${currentUser.role eq 'user'}">
            <th>Buy tickets</th>
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
            <c:if test="${currentUser.role eq 'user'}">
                <td>
                    <form name="buyTicket" action="buy.ticket" method="post">

                        <label for="date">Date:</label><br>
                        <input type="date" id="date" name="date"
                               value=${show.dateBegins} min=${show.dateBegins} max=${show.dateEnds}>

                        <label for="quantity">Q-ty:</label>
                        <select id="quantity" name="quantity">
                            <option value="1" selected="selected">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                        </select>
                        <input type="hidden" name="showId" value="${show.id}">
                        <input type="submit" value="buy">


                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>
</table>
<br/>


<form action="display.shows" method="post">
    <table>
        <tr>
            <td>
                Choose ordering of the show list:
            </td>
            <td>
                <input type="radio" id="bySubject" name="orderBy" value="bySubject">
                <label for="bySubject">by subject</label>

                <input type="radio" id="byDate" name="orderBy" value="byDate">
                <label for="byDate">by date</label>

                <input type="radio" id="byPrice" name="orderBy" value="byPrice">
                <label for="byPrice">by price</label>
            </td>
        </tr>
        <tr>
            <td>
                Select the date:
            </td>
            <td>
                <input type="date" id="someDate" name="someDate">
            </td>
        </tr>
        <tr>
            <td>
            </td>
            <td>
                <input type="submit" value="Submit"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
