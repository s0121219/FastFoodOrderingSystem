<!DOCTYPE html>
<html>
    <head>
        <title>Fast Food Ordering System</title>
    </head>
    <body>
        
        <h2>Create a User</h2>
        <form:form method="POST" enctype="multipart/form-data"
                   modelAttribute="itemUser">
            <form:label path="username">Username*</form:label><br/>
            <form:input type="text" path="username" required="required"/><br/><br/>
            
            <form:label path="password">Password*</form:label><br/>
            <form:input type="text" path="password" required="required"/><br/><br/>
            
            <form:label path="fullname">Fullname*</form:label><br/>
            <form:input type="text" path="fullname" required="required"/><br/><br/>
            
            <form:label path="phoneNumber">Phone Number</form:label><br/>
            <form:input type="text" path="phoneNumber" /><br/><br/>
            
            <form:label path="deliveryAddress">Delivery Address</form:label><br/>
            <form:input type="text" path="deliveryAddress" /><br/><br/>
            
            <br /><br />
            <input type="submit" value="Register"/>
        </form:form>
    </body>
</html>
