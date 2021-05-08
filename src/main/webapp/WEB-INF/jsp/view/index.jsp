<!DOCTYPE html>
<html>
    <head>
        <title>Fast Food Ordering System</title>
    </head>
    <body>
        <div  style="display: flex;">
            <form action="login" method="GET">
                <input type="submit" value="Login"/>
            </form>
            <p>&emsp;</p>
            <form action="register" method="GET">
                <input type="submit" value="Register"/>
            </form>
        </div>
        
        <c:if test="${param.logout != null}">
            <p>You have logged out.</p>
        </c:if>
        <h2>List of Items</h2>

        <c:choose>
            <c:when test="${fn:length(itemDatabase) == 0}">
                <i>There are no items in the system.</i>
            </c:when>
            <c:otherwise>
                <c:forEach items="${itemDatabase}" var="item">
                    Item ${item.id}:
                    <a href="<c:url value="/index_view/${item.id}" />">
                        <c:out value="${item.itemName}" /></a><br>

                </c:forEach>
            </c:otherwise>
        </c:choose>
    </body>
</html>