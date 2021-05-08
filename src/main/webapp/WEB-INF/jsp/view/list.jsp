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

        <h2>List of Items</h2>
        <security:authorize access="hasRole('ADMIN')">
            [<a href="<c:url value="/item/create" />">Create a Item</a>]
            [<a href="<c:url value="/user" />">Manage User Accounts</a>]

        </security:authorize>

        [<a href="<c:url value="/item/shoppingcart" />">View Shopping cart</a>]<br /><br />

        <c:choose>
            <c:when test="${fn:length(itemDatabase) == 0}">
                <i>There are no items in the system.</i>
            </c:when>
            <c:otherwise>
                <c:forEach items="${itemDatabase}" var="item">
                    Item ${item.id}:
                    <a href="<c:url value="/item/view/${item.id}" />">
                        <c:out value="${item.itemName}" /></a>
                        <c:choose>
                            <c:choose>
                                <c:when test="">
                                <c:when test="${item.availability}">
                                [<a href="<c:url value="/item/addShoppingcart/${item.id}" />">Add to Shopping Cart</a>]
                            </c:when>
                            <c:otherwise>[Unavailable]</c:otherwise>
                            </c:when>
                        </c:choose>

                    </c:choose>
                    <security:authorize access="hasRole('ADMIN')">
                        [<a href="<c:url value="/item/edit/${item.id}" />">Edit</a>]
                        [<a href="<c:url value="/item/delete/${item.id}" />">Delete</a>]<br />
                    </security:authorize>


                </c:forEach>
            </c:otherwise>
        </c:choose>
    </body>
</html>