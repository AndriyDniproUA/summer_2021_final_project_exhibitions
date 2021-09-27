<%@include file="/jspf/head.jspf" %>

<html>
<head>
    <title>Registration</title>
    <script src="js/validateForms.js"></script>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>

<body>
<%@include file="/jsp/pageHead.jsp"%>
<h2><fmt:message key='register_jsp.label.please_register'/></h2><br>
<form action="register" method="post" onsubmit="return validateRegistrationForm()">
    <label for="login"><fmt:message key='register_jsp.label.login'/>:</label><br>
    <input type="text" id="login" name="login"><br>

    <label for="password"><fmt:message key='register_jsp.label.password'/>:</label><br>
    <input type="password" id="password" name="password"><br>

    <label for="password2"><fmt:message key='register_jsp.label.confirm_password'/>:</label><br>
    <input type="password" id="password2" name="password2">

    <input type="submit" value="<fmt:message key='register_jsp.button.submit'/>">
</form>
</body>
</html>

