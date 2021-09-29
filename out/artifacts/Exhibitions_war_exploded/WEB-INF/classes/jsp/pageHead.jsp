<c:if test="${not empty param.locale}">
    <fmt:setLocale value="${param.locale}" scope="session"/>
    <c:set var="currentLocale" value="${param.locale}" scope="session"/>
</c:if>

<fmt:setBundle basename="labels"/>

<table class="styled-table">
    <tr>
        <td>
            <h1><fmt:message key="pageHead_jsp.label.site_name"/></h1>
        </td>
        <td>
            <form action="display.shows">
                <input type="submit" value="<fmt:message key='pageHead_jsp.button.shows'/>"/>
            </form>
        </td>

        <c:if test="${sessionScope.currentUser.role eq 'admin'}">
            <td>
                <form action="users">
                    <input type="submit" value="<fmt:message key='pageHead_jsp.button.users'/>"/>
                </form>
            </td>

            <td>
                <form action="add.show">
                    <input type="submit" value="<fmt:message key='pageHead_jsp.button.add_show'/>"/>
                </form>
            </td>
        </c:if>

        <c:if test="${sessionScope.currentUser.role eq 'user'}">
            <td>
                <form action="personal.cabinet">
                    <input type="submit" value="<fmt:message key='pageHead_jsp.button.personal_cabinet'/>"/>
                </form>
            </td>
        </c:if>

        <c:if test="${not empty sessionScope.currentUser}">
            <td>

                <fmt:message key='pageHead_jsp.label.current_user'/>:${sessionScope.currentUser.login}
                <fmt:message key='pageHead_jsp.label.role'/>: ${sessionScope.currentUser.role}

                <c:if test="${sessionScope.currentUser.role eq 'user'}">
                    <fmt:message key='pageHead_jsp.label.balance'/>: ${sessionScope.currentUser.balance}
                </c:if>
            </td>
        </c:if>

        <c:if test="${empty sessionScope.currentUser}">
            <td>
                <form action="login">
                    <input type="submit" value="<fmt:message key='pageHead_jsp.button.login'/>"/>
                </form>
            </td>
        </c:if>

        <c:if test="${not empty sessionScope.currentUser}">
            <td>
                <form action="logout">
                    <input type="submit" value="<fmt:message key='pageHead_jsp.button.logout'/>"/>
                </form>
            </td>
        </c:if>
        <td>
            <form action="display.shows" method="post">
                <fmt:message key='pageHead_jsp.label.language'/>:
                <select name="locale">
                    <c:forEach items="${applicationScope.locales}" var="locale">

                        <c:if test="${empty currentLocale}">
                            <c:set var="selected"
                                   value="${locale.key eq 'en' ? 'selected' : '' }"/>
                        </c:if>
                        <c:if test="${not empty currentLocale}">
                            <c:set var="selected"
                                   value="${locale.key eq currentLocale ? 'selected' : '' }"/>
                        </c:if>

                        <option value="${locale.key}"
                            ${selected}>${locale.value}</option>
                    </c:forEach>
                </select>
                <input type="submit" value="<fmt:message key='pageHead_jsp.button.language'/>">
            </form>
        </td>
        <td>
            <mine:today/>
        </td>
    </tr>
</table>














