<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support</title>
    </head>
    <body>
        <h2>Items</h2>
        <a href="<c:url value="/item/create" />">Create a Item</a><br /><br />
        <c:choose>
            <c:when test="${fn:length(itemDatabase) == 0}">
                <i>There are no items in the system.</i>
            </c:when>
            <c:otherwise>
                <c:forEach items="${itemDatabase}" var="entry">
                    Item ${entry.key}:
                    <a href="<c:url value="/item/view/${entry.key}" />">
                        <c:out value="${entry.value.id}" /></a>
                    (item: <c:out value="${entry.value.itemName}" />)<br />
                    [<a href="<c:url value="/item/edit/${entry.key}" />">Edit</a>]
                    [<a href="<c:url value="/item/delete/${entry.key}" />">Delete</a>]<br />
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </body>
</html>