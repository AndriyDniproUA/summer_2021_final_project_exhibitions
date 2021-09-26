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
        <td>${ticket.subject}</td>  make reference!!!!!
        <td>${show.date}</td>
    <td>${show.quantity}</td>
    <td>${show.cost}</td>

    </tr>
    </c:forEach>
</table>

</form>
</body>
</html>