<%@include file="/jspf/head.jspf" %>

<html>
<head>
    <title>Personal update</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <script src="js/validateForms.js"></script>
</head>

<body>
<%@include file="/jsp/pageHead.jsp" %>

<h2><fmt:message key='personal_cabinet_update_user_jsp.label.update_personal_info'/>:</h2><br>
<form action="personal.cabinet" method="post" onsubmit="return validateRegistrationForm()">
    <table class="styled-table">
        <tr>
            <td>
                <label for="login"><fmt:message key='personal_cabinet_update_user_jsp.label.login'/>:</label><br>
            </td>
            <td>
                <input type="text" id="login" name="login" value="${currentUser.login}"><br>
                <input type="hidden" id="userId" name="id" value="${currentUser.id}"><br>
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key='personal_cabinet_update_user_jsp.label.your_role'/>:
            </td>
            <td>
                ${currentUser.role}<br>
            </td>
        </tr>
        <tr>
            <td>
                <label for="password"><fmt:message key='personal_cabinet_update_user_jsp.label.password'/>:</label>
            </td>
            <td>
                <input type="password" id="password" name="password" value="${currentUser.password}"><br>
            </td>
        </tr>
        <tr>
            <td>
                <label for="password2"><fmt:message key='personal_cabinet_update_user_jsp.label.confirm_password'/>:</label>
            </td>
            <td>
                <input type="password" id="password2" name="password2" value="${currentUser.password}"><br>
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key='personal_cabinet_update_user_jsp.label.deposit_account'/>:
            </td>
            <td>
                <select id="deposit" name="deposit">
                    <option value="0" selected="selected">0</option>
                    <option value="100">100</option>
                    <option value="200">200</option>
                    <option value="500">500</option>
                    <option value="1000">1000</option>
                </select>
            </td>
        </tr>

        <tr><td></td><td></td></tr>
        <tr>
            <td>
            </td>
            <td>
                <input type="submit" value="<fmt:message key='personal_cabinet_update_user_jsp.button.submit'/>">
            </td>
        </tr>
    </table>
</form>
</body>
</html>

