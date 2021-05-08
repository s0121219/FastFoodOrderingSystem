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

        <h2>Add an item</h2>
        <form:form method="POST" enctype="multipart/form-data"
                   modelAttribute="itemForm">
            <form:label path="itemName">Item Name</form:label><br />
            <form:input type="text" path="itemName" /><br /><br />

            <form:label path="itemDescription">Item Description</form:label><br />
            <form:input type="text" path="itemDescription" /><br /><br />


            <form:label path="price">Price</form:label><br />
            <form:input type="number" min="1" path="price" /><br /><br />


            <form:label path="availability">Availability</form:label><br />
            <form:checkbox path = "availability" /><br /><br />

            <b>Upload Photo(Optional)</b><br />
            <input type="file" accept="image/*" name="attachments" multiple="multiple" /><br /><br />
            <input type="submit" value="Submit"/>
        </form:form>
    </body>
</html>