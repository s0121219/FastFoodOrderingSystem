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

        <h2>Item #${item.id}</h2>
        <form:form method="POST" enctype="multipart/form-data" 
                   modelAttribute="itemForm">
            <form:label path="itemName">Item Name</form:label><br />
            <form:input type="text" path="itemName" value="${item.itemName}" /><br /><br />

            <form:label path="itemDescription">Item Description</form:label><br />
            <form:input type="text" path="itemDescription" value="${item.itemDescription}" /><br /><br />


            <form:label path="price">Price</form:label><br />
            <form:input type="text" path="price" value="${item.price}" /><br /><br />


            <form:label path="availability">Availability</form:label><br />
            <form:checkbox path = "availability" value="${item.availability}"/><br /><br />

            <c:if test="${fn:length(item.attachments) > 0}">
                <b>Attachments:</b><br/>
                <ul>
                    <c:forEach items="${item.attachments}" var="attachment">
                        <li>
                            <c:out value="${attachment.name}" />
                            [<a href="<c:url value="/item/${item.id}/delete/${attachment.name}" />">Delete</a>]
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
            <b>Add attachments</b><br />
            <input type="file" name="attachments" multiple="multiple"/><br/><br/>
            <input type="submit" value="Save"/>
        </form:form>
            <a href="<c:url value="/item" />">Return to item list</a>
    </body>
</html>