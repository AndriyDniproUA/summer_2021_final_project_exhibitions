<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
  <title>Warning</title>
  <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<%@include file="/jsp/pageHead.jsp"%>

<div class="error">
<h2>ERROR!</h2><br>
</div>

<div class="error">
  <h3>${errorMessage}</h3>
</div>

<%--<a href="users">See all users</a><br>--%>
<%--<a href="login">Login</a>--%>

</body>
</html>
