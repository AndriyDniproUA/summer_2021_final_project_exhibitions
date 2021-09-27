<%@include file="/jspf/head.jspf" %>

<html>
<head>
    <title>Personal cabinet</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <script src="js/validateForms.js"></script>
</head>

<body>
<%@include file="/jsp/pageHead.jsp" %>


<form action="personal.cabinet.update">
    <input type="submit" value="<fmt:message key='personal_cabinet_jsp.button.update_personal_info'/>"/>
</form>

<h3><fmt:message key='personal_cabinet_jsp.label.my_tickets'/></h3>
<table class="styled-table">
    <tr>
        <th><fmt:message key='personal_cabinet_jsp.label.order_no'/></th>
        <th><fmt:message key='personal_cabinet_jsp.label.subject'/></th>
        <th><fmt:message key='personal_cabinet_jsp.label.date'/></th>
        <th><fmt:message key='personal_cabinet_jsp.label.quantity'/></th>
        <th><fmt:message key='personal_cabinet_jsp.label.cost'/></th>
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