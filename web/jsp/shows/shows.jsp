<%@include file="/jspf/head.jspf" %>

<html>
<head>
    <title>Display shows</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<%@include file="/jsp/pageHead.jsp" %>

<h3><fmt:message key='shows_jsp.label.shows_list'/></h3>
<table class="styled-table">
    <tr>
        <th><fmt:message key='shows_jsp.label.item_num'/></th>
        <th><fmt:message key='shows_jsp.label.subject'/></th>
        <th><fmt:message key='shows_jsp.label.date_begins'/></th>
        <th><fmt:message key='shows_jsp.label.date_ends'/></th>
        <th><fmt:message key='shows_jsp.label.time_opens'/></th>
        <th><fmt:message key='shows_jsp.label.time_closes'/></th>
        <th><fmt:message key='shows_jsp.label.ticket_price'/></th>
        <th><fmt:message key='shows_jsp.label.rooms'/></th>
        <c:if test="${currentUser.role eq 'admin'}">
            <th><fmt:message key='shows_jsp.label.tickets_sold'/></th>
            <th><fmt:message key='shows_jsp.label.total_sum'/></th>
            <th><fmt:message key='shows_jsp.label.delete'/></th>
        </c:if>
        <c:if test="${currentUser.role eq 'user'}">
            <th><fmt:message key='shows_jsp.label.buy_tickets'/></th>
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
                <td>${show.ticketsSold}</td>
                <td>${show.total}</td>
                <td><a href="delete.show?id=${show.id}"><fmt:message key='shows_jsp.link.delete'/></a></td>
            </c:if>
            <c:if test="${currentUser.role eq 'user'}">
                <td>
                    <form name="buyTicket" action="buy.ticket" method="post">

                        <label for="date"><fmt:message key='shows_jsp.label.date'/>:</label><br>
                        <input type="date" id="date" name="date"
                               value=${show.dateBegins} min=${show.dateBegins} max=${show.dateEnds}>

                        <label for="quantity"><fmt:message key='shows_jsp.label.quantity'/>:</label>
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
                <fmt:message key='shows_jsp.label.choose_ordering'/>:
            </td>
            <td>
                <input type="radio" id="bySubject" name="orderBy" value="bySubject">
                <label for="bySubject"><fmt:message key='shows_jsp.label.by_subject'/></label>

                <input type="radio" id="byDate" name="orderBy" value="byDate">
                <label for="byDate"><fmt:message key='shows_jsp.label.by_date'/></label>

                <input type="radio" id="byPrice" name="orderBy" value="byPrice">
                <label for="byPrice"><fmt:message key='shows_jsp.label.by_price'/></label>
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key='shows_jsp.label.select_date'/>:
            </td>
            <td>
                <input type="date" id="someDate" name="someDate">
            </td>
        </tr>
        <tr>
            <td>
            </td>
            <td>
                <input type="submit" value="<fmt:message key='shows_jsp.button.submit'/>"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>


