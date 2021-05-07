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

        <h2>Item #${item.id}: <c:out value="${item.id}" /></h2>


        <security:authorize access="hasRole('ADMIN')">
            [<a href="<c:url value="/item/edit/${item.id}" />">Edit</a>]
            [<a href="<c:url value="/item/delete/${item.id}" />">Delete</a>]<br />
        </security:authorize>


        <i>Item Name - <c:out value="${item.itemName}" /></i><br /><br />
        <c:out value="${item.itemDescription}" /><br /><br />
        <c:if test="${fn:length(item.attachments) > 0}">
            Attachments:
            <c:forEach items="${item.attachments}" var="attachment"
                       varStatus="status">
                <c:if test="${!status.first}">, </c:if>
                <a href="<c:url value="/item/${item.id}/attachment/${attachment.name}" />">
                    <c:out value="${attachment.name}" /></a>
            </c:forEach><br /><br />
        </c:if>
        <a href="<c:url value="/item" />">Return to list items</a>
    </body>
</html>