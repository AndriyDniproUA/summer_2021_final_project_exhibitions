
<html>
<head>
    <title>Add show</title>
    <script src="js/validateForms.js"></script>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>

<body>
<%@include file="/jsp/pageHead.jsp" %>
<h2>Add show</h2><br>
<form name="addShow" action="add.show" method="post" onsubmit="return validateAddShowForm()">
    <table class="styled-table">
        <tr>
            <td>
                <label for="subject">Subject of the show:</label>
            </td>
            <td>
                <input type="text" id="subject" name="subject">
            </td>
        </tr>
        <tr>
            <td>
                <label for="date_begins">Start date:</label>
            </td>
            <td>
                <input type="date" id="date_begins" name="date_begins" value="2021-09-21">
            </td>
        </tr>
        <tr>
            <td>
                <label for="date_ends">End date:</label>
            </td>
            <td>
                <input type="date" id="date_ends" name="date_ends" value="2021-09-25">
            </td>
        </tr>
        <tr>
            <td>
                <label for="time_opens">Time opens:</label>
            </td>
            <td>
                <input type="time" id="time_opens" name="time_opens" value="08:00">
            </td>
        </tr>

        <tr>
            <td>
                <label for="time_closes">Time closes:</label><br>

            </td>
            <td>
                <input type="time" id="time_closes" name="time_closes" value="17:00"><br>
            </td>
        </tr>
        <tr>
            <td>
                <label for="price">Ticket price:</label><br>
            </td>
            <td>
                <select id="price" name="price">
                    <option value="50">50</option>
                    <option value="100" selected="selected">100</option>
                    <option value="150">150</option>
                    <option value="200">200</option>
                </select>
            </td>
        </tr>
        <tr>

            <c:if test="${not empty message}">
        <tr>
            <td>
            </td>
            <td>
                <div class="warning">
                    <br>${message}<br>
                </div>
            </td>
        </tr>
        </c:if>


        <tr>
            <td>
                Select rooms:<br>
            </td>
            <td>
                <c:forEach var="room" items="${allRooms}">
                    <input type="checkbox" id="${room.key}" name="rooms" value="${room.key}">
                    <label for="${room.key}">${room.key}</label>
                </c:forEach>
            </td>
        </tr>
        <tr>
            <td>
                <input type="reset" value="Reset">
            </td>
            <td>
                <input type="submit" value="Submit">
            </td>
        </tr>
    </table>
</form>
</body>
</html>