<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support</title>
    </head>
    <body>
        <h2>Item #${itemId}: <c:out value="${item.id}" /></h2>
        [<a href="<c:url value="/item/edit/${itemId}" />">Edit</a>]
        [<a href="<c:url value="/item/delete/${itemId}" />">Delete</a>]<br />
        <i>Item Name - <c:out value="${item.itemName}" /></i><br /><br />
        <c:out value="${item.itemDescription}" /><br /><br />
        <c:if test="${item.numberOfAttachments > 0}">
            Attachments:
            <c:forEach items="${item.attachments}" var="attachment"
                       varStatus="status">
                <c:if test="${!status.first}">, </c:if>
                <a href="<c:url value="/item/${itemId}/attachment/${attachment.name}" />">
                    <c:out value="${attachment.name}" /></a>
            </c:forEach><br /><br />
        </c:if>
        <a href="<c:url value="/item" />">Return to list items</a>
    </body>
</html>