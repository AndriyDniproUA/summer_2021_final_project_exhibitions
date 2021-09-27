<%@include file="/jspf/head.jspf" %>

<html>
<head>
    <title>Login Page</title>
    <script src="js/validateForms.js"></script>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>

<body>
<%@include file="/jsp/pageHead.jsp"%>
<h2><fmt:message key='login_jsp.label.please_login'/></h2><br>

<form action="login" method="post"  onsubmit="return validateLoginForm()">

    <label for="login"><fmt:message key='login_jsp.label.login'/>:</label><br>
    <input type="text" id="login" name="login" value="nick"><br>
    <label for="password"><fmt:message key='login_jsp.label.password'/>:</label><br>
    <input type="password" id="password" name="password" value="111">
    <input type="submit" value="Submit">

</form>
<br><br>

<form action="register">
    <input type="submit" value="<fmt:message key='login_jsp.link.register'/>" />
</form><br>

</body>
</html>






