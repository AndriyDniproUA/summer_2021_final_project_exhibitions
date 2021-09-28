<%@include file="/jspf/head.jspf" %>

<html>
<head>
    <title>Welcome</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<%@include file="/jsp/pageHead.jsp"%>

<h2><fmt:message key='welcome_jsp.label.welcome'/> ${currentUser.login}</h2><br>
<fmt:message key='welcome_jsp.label.current_role'/>: <c:out value="${currentUser.role}"/><br><br>
</body>
</html>
