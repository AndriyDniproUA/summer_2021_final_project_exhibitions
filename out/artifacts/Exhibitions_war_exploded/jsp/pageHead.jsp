<table class="styled-table">
    <tr>
        <td>
            <h1>Shows central</h1>
        </td>
        <td>
            <form action="display.shows">
                <input type="submit" value="SHOWS"/>
            </form>
        </td>

        <c:if test="${sessionScope.currentUser.role eq 'admin'}">
            <td>
                <form action="users">
                    <input type="submit" value="User List"/>
                </form>
            </td>

            <td>
                <form action="add.show">
                    <input type="submit" value="Add show"/>
                </form>
            </td>
        </c:if>

        <c:if test="${sessionScope.currentUser.role eq 'user'}">
            <td>
                <form action="personal.cabinet">
                    <input type="submit" value="Personal cabinet"/>
                </form>
            </td>
        </c:if>

        <c:if test="${not empty sessionScope.currentUser}">
            <td>
                Current user: ${sessionScope.currentUser.login}
                Role: ${sessionScope.currentUser.role}

                <c:if test="${sessionScope.currentUser.role eq 'user'}">
                    Balance: ${sessionScope.currentUser.balance}
                </c:if>
            </td>
        </c:if>

        <c:if test="${empty sessionScope.currentUser}">
            <td>
                <form action="login">
                    <input type="submit" value="Login"/>
                </form>
            </td>
        </c:if>

        <c:if test="${not empty sessionScope.currentUser}">
            <td>
                <form action="logout">
                    <input type="submit" value="Logout"/>
                </form>
            </td>
        </c:if>
    </tr>
</table>













