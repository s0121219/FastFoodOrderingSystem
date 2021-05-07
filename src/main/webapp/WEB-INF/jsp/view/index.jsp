<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support</title>
    </head>
    <body>
        <form action="login" method="GET">
            <input type="submit" value="Login"/>
        </form>
        
        

        <h2>Items</h2>
        
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

                </c:forEach>
            </c:otherwise>
        </c:choose>
    </body>
</html>