<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support</title>
    </head>
    <body>
        <h2>Add an item</h2>
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
            <b>Upload Photo(Optional)</b><br />
            <input type="file" name="attachments" multiple="multiple" /><br /><br />
            <input type="submit" value="Submit"/>
        </form:form>
    </body>
</html>