<%@ include file="header.jsp" %>

<script type="text/javascript">
    $(document).ready(function () {
        $('#confirmButton').click(processSaving);
        });

    function processSaving() {
        var stationName = $('#stationName').val();
        $.post('${pageContext.request.contextPath}/stations/addStation', 
                {'stationName': stationName});
        }
</script>

<h2>Station:</h2>

Name:
<input id="stationName" type="text" value="${station}"/>

    <c:if test="${!empty pathmapList}">
        <h3>Relations:</h3>
        <table border="1">
            <tr>
                <th width="20%">Id</th>
                <th width="20%">Current station</th>
                <th width="20%">Next station</th>
                <th width="10%">Cost</th>
                <th width="20%">Actions</th>   
            </tr>
            <c:forEach items="${pathmapList}" var="pathmap">
                <tr>
                    <td>${pathmap.id}</td>
                    <td>${pathmap.currentStation.name}</td>
                    <td>${pathmap.nextStation.name}</td>
                    <td>${pathmap.cost}</td>
                    <td><a href="${pageContext.request.contextPath}/stations/deleteRealtion/${pathmap.currentStation.name}/${pathmap.nextStation.name}">delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <h3>Bind new relation:</h3>

    <form:form method="post" action="${pageContext.request.contextPath}/stations/bindRelation" commandName="pathmap" >
        <form:hidden path="currentStation" value="${station}"/>
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
                    <input type="submit" value="Bind relation"/>
                </td>
            </tr>
        </table>  
    </form:form>
    
    <form>
        <p><input id="confirmButton" type="button" value="Confirm"></p>
  </form>

<h3>Existing stations:</h3>

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
                <td><a href="${pageContext.request.contextPath}/stations/editStation/${station.id}">edit</a> 
                    <a href="${pageContext.request.contextPath}/stations/deleteStation/${station.id}">delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>
