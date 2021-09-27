<html>
<head>
    <title>Login Page</title>
    <script src="js/validateForms.js"></script>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>

<body>
<%@include file="/jsp/pageHead.jsp"%>
<h2>Please login</h2><br>

<form action="login" method="post"  onsubmit="return validateLoginForm()">

    <label for="login">Login:</label><br>
    <input type="text" id="login" name="login" value="nick"><br>
    <label for="password">Password:</label><br>
    <input type="password" id="password" name="password" value="111">
    <input type="submit" value="Submit">

</form>
<br><br>

<form action="register">
    <input type="submit" value="Register" />
</form><br>

</body>
</html>




