
<html>
<head>
    <title>Registration</title>
    <script src="js/validateForms.js"></script>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>

<body>
<%@include file="/jsp/pageHead.jsp"%>
<h2>Please register</h2><br>
<form action="register" method="post" onsubmit="return validateRegistrationForm()">
    <label for="login">Login:</label><br>
    <input type="text" id="login" name="login"><br>

    <label for="password">Password:</label><br>
    <input type="password" id="password" name="password"><br>

    <label for="password2">Confirm password:</label><br>
    <input type="password" id="password2" name="password2">

    <input type="submit" value="Submit">
</form>
</body>
</html>