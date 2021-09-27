<html>
<head>
    <title>Personal cabinet</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <script src="js/validateForms.js"></script>
</head>

<body>
<%@include file="/jsp/pageHead.jsp" %>


<form action="personal.cabinet.update">
    <input type="submit" value="Update personal information"/>
</form>

<h3>Tickets List</h3>
<table class="styled-table">
    <tr>
        <th>Order No.</th>
        <th>Subject</th>
        <th>Date</th>
        <th>Q-ty</th>
        <th>Cost</th>
        <c:forEach var="ticket" items="${tickets}" varStatus="theCount">
    <tr>
        <td>${ticket.order_id}</td>
        <td><a href="display.single.show?show_id=${ticket.show_id}">${ticket.subject}</a></td>

        <td>${ticket.date}</td>
        <td>${ticket.quantity}</td>
        <td>${ticket.cost}</td>

    </tr>
    </c:forEach>
</table>

</form>
</body>
</html>