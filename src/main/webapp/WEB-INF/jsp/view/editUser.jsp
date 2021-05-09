<!DOCTYPE html>
<html>
    <head>
        <title>Fast Food Ordering System</title>
    </head>
    <body>
        
        <h2>Edit info</h2>
        <form:form method="POST" enctype="multipart/form-data"
                   modelAttribute="userForm">
            <form:label path="username">Username</form:label><br/>
            <c:choose>
                <c:when test="${User.username!='admin'}">
                    <form:input type="text" path="username" value="${itemUser.username}" disabled="true" required="required"/><br/><br/>
                </c:when>
                <c:otherwise>
                    <form:input type="text" path="username" value="${itemUser.username}"  required="required"/><br/><br/>
                </c:otherwise>
            </c:choose>
            
            <form:label path="password">Password</form:label><br/>
            <form:input type="text" path="password" value="${itemUser.password}" required="required"/><br/><br/>
            
            <form:label path="fullname">Fullname</form:label><br/>
            <form:input type="text" path="fullname" value="${itemUser.fullname}" required="required"/><br/><br/>
            
            <form:label path="phoneNumber">Phone Number</form:label><br/>
            <form:input type="text" path="phoneNumber" value="${itemUser.phoneNumber}"/><br/><br/>
            
            <form:label path="deliveryAddress">Delivery Address</form:label><br/>
            <form:input type="text" path="deliveryAddress" value="${itemUser.deliveryAddress}"/><br/><br/>
            
            <br /><br />
            <input type="submit" value="Edit User"/>
        </form:form>
    </body>
</html>
