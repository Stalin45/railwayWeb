<%@ include file="header.jsp" %>
<!--<script type="text/javascript">
$(document).ready(function() {
 $('#station').change(function(){
            var obj = $('#station :selected').text()
            $.ajax({
                url: 'pathmap',
                type: 'POST',
                data: ({
                    station: obj
                })
            });
        });
    });
</script>-->

<!--<select id="station">
<%--<c:forEach items="${stationList}" var="station">--%>
    <option>
    <%--${station.name}--%>
    </option>    
<%--</c:forEach>--%>
</select>-->
<form action="${pageContext.request.contextPath}/pathmaps/getRelations" method="POST">
    <select name="station"/>
    <c:forEach items="${stationList}" var="station">
        <option>${station.name}</option>    
    </c:forEach>
</select>
<input type="submit">
</form>

<h3>Relations:</h3>

<c:if test="${!empty pathmapList}">
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

<form:form method="post" action="${pageContext.request.contextPath}/pathmaps/addRelation" commandName="pathmap" >
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