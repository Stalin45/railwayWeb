<%@ include file="header.jsp" %>

<h2>Add a new station:</h2>

<form:form method="post" action="${pageContext.request.contextPath}/stations/addStation" commandName="station">
    <table>
        <tr>
            <td>Name <form:input path="name"></form:input></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Add station"/>
                </td>
            </tr>
        </table>  
</form:form>

<c:if test="${!empty pathmapList}">
    <h3>Relations:</h3>
    <table border="1">
        <tr>
            <th width="20%">Id</th>
            <th width="20%">Next station</th>
            <th width="10%">Cost</th>
            <th width="20%">Actions</th>   
        </tr>
        <c:forEach items="${pathmapList}" var="pathmap">
            <tr>
                <td>${pathmap.id}</td>
                <td>${pathmap.nextStation}</td>
                <td>${pathmap.cost}</td>
                <td></td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<h3>Add a new relation</h3>

<form:form method="post" action="${pageContext.request.contextPath}/stations/addRelation" commandName="pathmap" >
    <form:hidden path="currentStation" value="${station.id}"/>
    <table>
        <tr>
            <td>Next station: </td>
            <td><form:select path="nextStation" items="${nextStationList}" itemLabel="name" itemValue="id"/></td>
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

<h3>Stations:</h3>

<c:if  test="${!empty stationList}">
    <table border="1">
        <tr>
            <th width="10%">id</th>
            <th width="20%">Name</th>
            <th width="20%">Actions</th>
        </tr>
        <c:forEach items="${stationList}" var="station">
            <tr>
                <td>${station.id}</td>
                <td>${station.name}</td>
                <td><a href="${pageContext.request.contextPath}/stations/edit/${station.id}">edit</a> <a href="{pageContext.request.contextPath}/stations/delete/${station.id}">delete</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
