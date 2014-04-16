<%@ include file="header.jsp" %>

<h2>Create new path for trains:</h2>

<c:if test="${!empty destinationList}">
    <h3>Destinations:</h3>
    <table border="1">
        <tr>
            <th width="20%">Station</th>
            <th width="20%">Time</th>  
        </tr>
        <c:forEach items="${destinationList}" var="destination">
            <tr>
                <td>${destination.station}</td>
                <td>${destination.time}</td>
                <td>${destination.number}</td>
                <td></td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<h3>Push new destination to the path</h3>

<form:form method="post" action="${pageContext.request.contextPath}/paths/pushDestination" commandName="destination" >
    <table>
        <tr>
            <td>Next station: </td>
            <td><form:select path="station" items="${nextStationList}" itemLabel="name" itemValue="id"/></td>
        </tr>
        <tr>
            <td>Cost: </td> 
            <td><form:input path="cost"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Add relation"/>
            </td>
        </tr>
    </table>  
</form:form>

<h3>Paths #:</h3>

<c:if  test="${!empty pathList}">
    <table border="1">
        <tr>
            <th width="10%">#</th>
            <th width="20%">Actions</th>
        </tr>
        <c:forEach items="${pathList}" var="path">
            <tr>
                <td>${path.number}</td>
                <td><a href="${pageContext.request.contextPath}/paths/edit/${path.number}">edit</a> <a href="{pageContext.request.contextPath}/paths/delete/${path.number}">delete</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
