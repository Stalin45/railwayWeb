<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <%@include file = "parts/includes.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=utf8">
        <title>Train type management</title>
        <script type ="text/javascript">
            $(document).ready(function() {
                
                $('.ajaxlink').click(function() {
                    $.ajax({
                        url: this,
                        type: 'GET',
                        success: function(data) {
                            success(data);
                        }
                    });
                })
            });

            function success(data) {
                if (data.path.id == null) {
                    $("#pathNumber").html("<h3>Path: new path</h3>");
                } else {
                    $("#pathNumber").html("<h3>Path: " + data.path.id + "</h3>");
                }
                var destinationTable = '';
                var nextStationSelect = '';
                for (var i in data.destinationList) {
                    var time = new Date(data.destinationList[i].time);
                    var h = time.getHours();
                    var m = time.getMinutes();
                    destinationTable = destinationTable
                            + "<tr><td>" + data.destinationList[i].number + "</td>\n"
                            + "<td>" + data.destinationList[i].stationName + "</td>\n"
                            + "<td>" + h + ":" + m + "</td>\n</tr>";
                }
                for (var i in data.nextStationList) {
                    nextStationSelect = nextStationSelect
                            + "<option value=\"" + data.nextStationList[i].id + "\"> "
                            + data.nextStationList[i].name + "</option>"
                }
                $("#next-station-select").html(nextStationSelect);
                $("#destination-table").html(destinationTable);
            }
        </script>
    </head>

    <body> 
        <div class="wrapper">
            <%@include file = "parts/header.jsp" %>
            <div class="middle">
                <%@include file = "parts/sidebar.jsp" %>
                <div class="container">

                    <h3>Train type: <c:if test="${trainType.id == null}">new type</c:if>
                        <c:if test="${trainType.id ne null}">${trainType.type}</c:if>
                    </h3>
                    
                    <form:form method="post" action="${pageContext.request.contextPath}/trainTypes/addTrainType" commandName="trainType" >
                        <form:hidden path="id" value="${trainType.id}"/>
                        <table>
                            <tr>
                                <td>Type: </td>
                                <td><form:input path="type"/></td>
                            </tr>
                            <tr>
                                <td>Cost multiplier: </td>
                                <td><form:input path="costMultiplier"/></td>
                            </tr>
                            <tr>
                                <td>Max seats: </td>
                                <td><form:input path="maxSeats"/></td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <input type="submit" value="Add train type"/>
                                </td>
                            </tr>
                        </table>  
                    </form:form>

                    <h3>Existing train types:</h3>

                    <c:if  test="${!empty trainTypeList}">
                        <table class="table-entity-list">
                            <tr>
                                <th width="100px">id</th>
                                <th width="200px">Type</th>
                                <th width="200px">Cost multiplier</th>
                                <th width="200px">Max seats</th>
                                <th width="200px">Actions</th>
                            </tr>
                            <c:forEach items="${trainTypeList}" var="trainType">
                                <tr>
                                    <td>${trainType.id}</td>
                                    <td>${trainType.type}</td>
                                    <td>${trainType.costMultiplier}</td>
                                    <td>${trainType.maxSeats}</td>
                                    <td><a href="${pageContext.request.contextPath}/trainTypes/editType/${trainType.id}">edit</a> 
                                        <a href="${pageContext.request.contextPath}/trainTypes/deleteType/${trainType.id}">delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:if>
                </div>
            </div>
        </div>
        <%@include file = "parts/footer.jsp" %>
    </body>

</html>
