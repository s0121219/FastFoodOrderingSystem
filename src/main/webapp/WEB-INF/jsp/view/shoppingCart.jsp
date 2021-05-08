<!DOCTYPE html>
<html>
    <head>
        <title>Fast Food Ordering System</title>
    </head>
    <body>
        <c:url var="logoutUrl" value="/logout"/>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>Shopping Cart</h2>
            
        <c:choose>
            <c:when test="${fn:length(itemDatabase) == 0}">
                <i>There are no items in your shopping cart.</i>
            </c:when>
            <c:otherwise>
                <c:forEach items="${itemDatabase}" var="item">
                    Item ${item.id}:
                    <a href="<c:url value="/item/view/${item.id}" />">
                        <c:out value="${item.itemName}" /></a>

                </c:forEach>
            </c:otherwise>
        </c:choose>
    </body>
</html>