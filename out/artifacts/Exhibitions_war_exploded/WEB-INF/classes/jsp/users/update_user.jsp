<%@include file="/jspf/head.jspf" %>

<html>
<head>
    <title>Update User</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <script src="js/validateForms.js"></script>
</head>

<body>
<%@include file="/jsp/pageHead.jsp" %>


<h2><fmt:message key='update_user_jsp.label.update_user'/></h2><br>
<form action="update.user" method="post" onsubmit="return validateRegistrationForm()">
    <table class="styled-table">
        <tr>
            <td><label for="login"><fmt:message key='update_user_jsp.label.login'/>:</label><br></td>
            <td><input type="text" id="login" name="login" value="${user.login}"><br>
                <input type="hidden" id="userId" name="id" value="${user.id}"></td>
        </tr>
        <tr>
            <td><label for="password"><fmt:message key='update_user_jsp.label.password'/>:</label></td>
            <td><input type="password" id="password" name="password" value="${user.password}"><br></td>
        </tr>
        <tr>
            <td><label for="password2"><fmt:message key='update_user_jsp.label.confirm_password'/>:</label></td>
            <td><input type="password" id="password2" name="password2" value="${user.password}"><br></td>
        </tr>

        <tr>
            <td><fmt:message key='update_user_jsp.label.current_role'/>: <br></td>
            <td>${user.role}</td>
        </tr>
        <tr>
            <td><label for="roles"><fmt:message key='update_user_jsp.label.select_role'/>:</label></td>
            <td><select id="roles" name="role">
                <option value="admin"><fmt:message key='update_user_jsp.option.admin'/></option>
                <option value="user" selected="selected"><fmt:message key='update_user_jsp.option.user'/></option>
            </select></td>
        </tr>
        <tr>
            <td><fmt:message key='update_user_jsp.label.current_balance'/></td>
            <td>${user.balance}</td>
        </tr>
        <tr>
            <td><fmt:message key='update_user_jsp.label.deposit_account'/>:</td>
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
        <tr>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value=" <fmt:message key='update_user_jsp.button.submit'/>"></td>
        </tr>
    </table>
</form>
</body>
</html>