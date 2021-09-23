<table class="styled-table">
    <tr>
        <td>
            <h2>Shows central</h2>
        </td>
        <td>
            <form action="display.shows">
                <input type="submit" value="SHOWS"/>
            </form>
        </td>
        <td>
            <form action="login">
                <input type="submit" value="Login"/>
            </form>
        </td>
        <td>
            <form action="logout">
                <input type="submit" value="Logout"/>
            </form>
        </td>

        <c:if test="${sessionScope.currentUser.role eq 'admin'}">
            <td>
                <form action="users">
                    <input type="submit" value="USERS"/>
                </form>
            </td>
        </c:if>

        <c:if test="${not empty sessionScope.currentUser}">
            <td>
                Current user: ${sessionScope.currentUser.login}
                Role: ${sessionScope.currentUser.role}
            </td>
        </c:if>
    </tr>
</table>













