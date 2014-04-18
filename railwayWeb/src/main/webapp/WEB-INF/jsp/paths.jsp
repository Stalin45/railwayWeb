<%@ include file="header.jsp" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.ui.timepicker.js"></script> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/jquery.ui.timepicker.css"/>

<script type="text/javascript">
    $(function () {
            $('#time').timepicker({
                showPeriodLabels: false, 
                hourText: 'Hours', 
                minuteText: 'Minutes', 
            showMinutes: true, 
            rows: 4, 
            timeSeparator: ':', 
            hours: {
                starts: 0, 
                ends: 23 
            },
            minutes: {
                starts: 0, 
                ends: 55, 
                interval: 5 
            },
        });
    });
</script>

<h2>Create new path for trains:</h2>

<h3>Path: <c:if test="${path.number == null}">new path</c:if>
          <c:if test="${path.number ne null}">${path.number}</c:if>
</h3>

<c:if test="${!empty destinationList}">
    <h3>Destinations:</h3>
    <table border="1">
        <tr>
            <th width="10%">Number</th>
            <th width="20%">Station</th>
            <th width="20%">Time</th>  
        </tr>
        <c:forEach items="${destinationList}" var="destination">
            <tr>
                <td>${destination.number}</td>
                <td>${destination.station.name}</td>
                <td><fmt:formatDate pattern="hh:mm" value="${destination.time}" /></td>
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
            <td>Time: </td> 
            <td><form:input id="time" path="time"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Push destination"/>
            </td>
        </tr>
    </table>  
</form:form>

<h3>Paths:</h3>

<c:if  test="${!empty pathList}">
    <table border="1">
        <tr>
            <th width="10%">Number</th>
            <th width="20%">Actions</th>
        </tr>
        <c:forEach items="${pathList}" var="path">
            <tr>
                <td>${path.number}</td>
                <td><a href="${pageContext.request.contextPath}/paths/editPath/${path.number}">edit</a> 
                    <a href="${pageContext.request.contextPath}/paths/deletePath/${path.number}">delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>
