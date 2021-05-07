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

        <h2>Item #${itemtId}</h2>
        <form:form method="POST" enctype="multipart/form-data" 
                   modelAttribute="itemForm">
            <form:label path="itemName">Item Name</form:label><br />
            <form:input type="text" path="itemName" /><br /><br />

            <form:label path="itemDescription">Item Description</form:label><br />
            <form:textarea path="itemDescription" rows="5" cols="30" /><br /><br />


            <form:label path="price">Price</form:label><br />
            <form:input type="text" path="price" /><br /><br />


            <form:label path="availability">Availability</form:label><br />
            <form:checkbox path = "availability" /><br /><br />

            <c:if test="${item.numberOfAttachments > 0}">
                <b>Attachments:</b><br/>
                <ul>
                    <c:forEach items="${item.attachments}" var="attachment">
                        <li>
                            <c:out value="${attachment.name}" />
                            [<a href="<c:url value="/ticket/${ticketId}/delete/${attachment.name}" />">Delete</a>]
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
            <b>Add attachments</b><br />
            <input type="file" name="attachments" multiple="multiple"/><br/><br/>
            <input type="submit" value="Save"/>
        </form:form>
    </body>
</html>