<%@include file="/jspf/head.jspf" %>

<html>
<head>
  <title>Error</title>
  <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<%@include file="/jsp/pageHead.jsp"%>

<div class="error">
<h2><fmt:message key='error_jsp.label.error'/>!</h2><br>
</div>

<div class="error">
  <h3>${errorMessage}</h3>
</div>

</body>
</html>


