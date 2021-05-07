<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support</title>
    </head>
    <body>
        <c:url var="logoutUrl" value="/logout"/>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>Items</h2>
        <security:authorize access="hasRole('ADMIN')">
            <a href="<c:url value="/item/create" />">Create a Item</a><br /><br />
            <a href="<c:url value="/user" />">Manage User Accounts</a><br /><br />

        </security:authorize>
        <c:choose>
            <c:when test="${fn:length(itemDatabase) == 0}">
                <i>There are no items in the system.</i>
            </c:when>
            <c:otherwise>
                <c:forEach items="${itemDatabase}" var="item">
                    Item ${item.id}:
                    <a href="<c:url value="/item/view/${item.id}" />">
                        <c:out value="${item.id}" /></a>
                    (item: <c:out value="${item.itemName}" />)<br />

                    <security:authorize access="hasRole('ADMIN')">
                        [<a href="<c:url value="/item/edit/${item.id}" />">Edit</a>]
                        [<a href="<c:url value="/item/delete/${item.id}" />">Delete</a>]<br />
                    </security:authorize>


                </c:forEach>
            </c:otherwise>
        </c:choose>
    </body>
</html>