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

        <h2>Item #${item.id}: <c:out value="${item.id}" /></h2>


        <security:authorize access="hasRole('ADMIN')">
            [<a href="<c:url value="/item/edit/${item.id}" />">Edit</a>]
            [<a href="<c:url value="/item/delete/${item.id}" />">Delete</a>]<br />
        </security:authorize>


        <i>Item Name - <c:out value="${item.itemName}" /></i><br /><br />
        <c:if test="${fn:length(item.attachments) > 0}">

            <c:forEach items="${item.attachments}" var="attachment"
                       varStatus="status">
                <c:if test="${!status.first}">&ensp; </c:if>
                <img width="256" height="256" src="/FastFoodOrderingSystem/item/${item.id}/attachment/${attachment.name}">

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

        Comment:
        <c:forEach items="${comment}" var="cm">
            <br /><br />${cm.content}
            <security:authorize access="hasRole('ADMIN')">
                [<a href="<c:url value="/item/view/${item.id}/deletecomment/${cm.id}" />">Delete</a>]
            </security:authorize>
        </c:forEach>

        <br />-------------------------------------<br /><br />
        <form:form method="POST" enctype="multipart/form-data" 
                   modelAttribute="commentForm">
            <form:label path="content">Write your comment</form:label><br />
            <form:input type="textarea" path="content" rows="10" cols="30" /><br /><br />
            <input type="submit" value="Submit"/>
        </form:form>

        <br />-------------------------------------<br />
        <a href="<c:url value="/item" />">Return to item list</a>
    </body>
</html>