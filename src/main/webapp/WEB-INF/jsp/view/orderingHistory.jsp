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

        <h2>Ordering History</h2>


        <c:choose>
            <c:when test="${fn:length(shoppingcartDatabase) == 0}">
                <i>There are no items in your shopping cart.</i>
            </c:when>
            <c:otherwise>
                <c:forEach items="${shoppingcartDatabase}" var="sc">
                    <c:forEach items="${itemDatabase}" var="item">
                        <c:if test="${sc.item_id==item.id}">
                            <c:if test="${sc.date !=null}">
                                Item ${item.id}:
                                <a href="<c:url value="/item/view/${item.id}" />">
                                    <c:out value="${item.itemName}" /></a> - ${sc.date}<br/><br/>
                                </c:if>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                </c:otherwise>
            </c:choose>

        <br/> <br/><a href="<c:url value="/item" />">Return to item list</a>
    </body>
</html>