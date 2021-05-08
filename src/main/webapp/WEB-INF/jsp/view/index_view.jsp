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

        <h2>Item #${item.id}: <c:out value="${item.itemName}" /></h2>

        <i>Item Name - <c:out value="${item.itemName}" /></i><br /><br />
        <c:if test="${fn:length(item.attachments) > 0}">
        
        <c:forEach items="${item.attachments}" var="attachment"
                       varStatus="status">
                <c:if test="${!status.first}">&ensp; </c:if>
                    <img width="256" height="256" src="/FastFoodOrderingSystem/${item.id}/attachment/${attachment.name}">
                   
            </c:forEach><br /><br />
            </c:if>
        Item description: <c:out value="${item.itemDescription}" /><br /><br />
        Price: $<c:out value="${item.price}" /><br /><br />
        <c:choose>
        <c:when test="${item.availability}">
            Available for order.<br /><br />
        </c:when>
        <c:otherwise>
            Unavailable for order.<br /><br />
        </c:otherwise>
        </c:choose>
        <a href="<c:url value="/" />">Return to item list</a>
    </body>
</html>