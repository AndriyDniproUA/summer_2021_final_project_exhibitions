<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Add show</title>
    <script src="js/validateForms.js"></script>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>

<body>
<%@include file="/jsp/pageHead.jsp"%>
<h2>Add show</h2><br>
<form name="addShow" action="add.show" method="post" onsubmit="return validateAddShowForm()">
    <br>


    <label for="subject">Subject of the show:</label><br>
    <textarea id="subject" name="subject"
              rows="2" cols="33"></textarea>

    <input type="reset" value="Reset">
    <br>

    <label for="date_begins">Start date:</label><br>
    <input type="date" id="date_begins" name="date_begins" value="2021-09-21"><br>

    <label for="date_ends">End date:</label><br>
    <input type="date" id="date_ends" name="date_ends" value="2021-09-25"><br>

    <label for="time_opens">Time opens:</label><br>
    <input type="time" id="time_opens" name="time_opens" value="08:00"><br>

    <label for="time_closes">Time closes:</label><br>
    <input type="time" id="time_closes" name="time_closes" value="17:00"><br>

    <label for="price">Ticket price:</label><br>

    <select id="price" name="price">
        <option value="50">50</option>
        <option value="100" selected="selected">100</option>
        <option value="150">150</option>
        <option value="200">200</option>
    </select>
    <br>

    <c:if test="${not empty message}">
        <div class="warning">
            <br>${message}<br>
        </div>
    </c:if>

    Select rooms:<br>
    <c:forEach var="room" items="${allRooms}">
        <input type="checkbox" id="${room.key}" name="rooms" value="${room.key}">
        <label for="${room.key}">${room.key}</label>
    </c:forEach>

    <input type="submit" value="Submit">
</form>

<%--<form action="display.shows">--%>
<%--    <input type="submit" value="Shows" />--%>
<%--</form>--%>

</body>
</html>