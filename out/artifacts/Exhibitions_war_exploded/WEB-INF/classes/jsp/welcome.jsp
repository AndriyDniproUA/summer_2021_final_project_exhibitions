
/<html>
<head>
    <title>Welcome</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<%@include file="/jsp/pageHead.jsp"%>

<h2>Welcome ${currentUser.login}</h2><br>
Your current role is: <c:out value="${currentUser.role}"/><br><br>
</body>
</html>
