<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:if test="${not empty param.locale}">
    <fmt:setLocale value="${param.locale}" scope="session"/>
    <c:set var="currentLocale" value="${param.locale}" scope="session"/>
</c:if>

<fmt:setBundle basename="lables"/>


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
        <td>
            <form action="display.shows" method="post">
                <fmt:message key="settings_jsp.label.set_locale"/>:
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
                <input type="submit" value=
                        "<fmt:message key='settings_jsp.form.submit_save_locale'/>">
            </form>
            current locale: ${currentLocale}
        </td>
    </tr>
</table>













