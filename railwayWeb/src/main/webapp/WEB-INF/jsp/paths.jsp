<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <%@include file = "parts/includes.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=utf8">
        <title>Path management</title>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.ui.timepicker.js"></script> 
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/jquery.ui.timepicker.css"/>
                <script type="text/javascript">
            $(document).ready(function() {
//                $('#confirmButton').click(processSaving);
                
                $('#pushDestinationButton').click(function() {
                    var stationId = $('#next-station-select option:selected').val();
                    var stationName = $('#next-station-select option:selected').text();
                    var time = $('#time').val();
                    $.ajax({
                        url: '${pageContext.request.contextPath}/paths/pushDestination',
                        type: 'POST',
                        data: ({
                            stationId: stationId,
                            stationName: stationName,
                            timeString: time
                        }),
                        success: function(data) {
                            success(data);
                        }
                    });
                })
                
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
                    var h = time.getHours() - 1;
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

//            function processSaving() {
//                $.post('${pageContext.request.contextPath}/paths/addPath'}, 
//                       function(data) {window.location.replace("${pageContext.request.contextPath}/stations")});
//            }
            
            $(function() {
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
    </head>

    <body> 
        <div class="wrapper">
            <%@include file = "parts/header.jsp" %>
            <div class="middle">
                <%@include file = "parts/sidebar.jsp" %>
                <div class="container">
                    <div id="pathNumber">
                        <h3>Path: <c:if test="${path.id == null}">new path</c:if>
                            <c:if test="${path.id ne null}">${path.id}</c:if>
                        </h3>
                    </div>
                    <%--<c:if test="${!empty destinationList}">--%>
                        <h3>Destinations:</h3>
                        <table class="table-entity-list">
                            <tr>
                                <th width="10%">Number</th>
                                <th width="20%">Station</th>
                                <th width="20%">Time</th>  
                            </tr>
                            <tbody id="destination-table">
                            <c:forEach items="${destinationList}" var="destination">
                                <tr>
                                    <td>${destination.number}</td>
                                    <td>${destination.stationName}</td>
                                    <td><fmt:formatDate pattern="hh:mm" value="${destination.time}" /></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    <%--</c:if>--%>

                    <h3>Push new destination to the path</h3>

                    <form method="post">
                        <table>
                            <tr>
                                <td>Next station: </td>
                                <td><select id="next-station-select" items="${nextStationList}" >
                                        <c:forEach items="${nextStationList}" var="nStation">
                                            <option value="${nStation.id}">${nStation.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>Time: </td> 
                                <td><input type="time" id="time"/></td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <input id="pushDestinationButton" type="button" value="Push destination"/>
                                </td>
                            </tr>
                        </table>  
                    </form>

                    <form method="POST" action="${pageContext.request.contextPath}/paths/addPath">
                        <p><input id="confirmButton" type="Submit" value="Confirm"></p>
                    </form>

                    <h3>Paths:</h3>

                    <c:if  test="${!empty pathList}">
                        <table class="table-entity-list">
                            <tr>
                                <th width="10%">Number</th>
                                <th width="20%">Actions</th>
                            </tr>
                            <c:forEach items="${pathList}" var="path">
                                <tr>
                                    <td>${path.id}</td>
                                    <td><a class="ajaxlink" href="${pageContext.request.contextPath}/paths/editPath/${path.id}" onclick="return false">edit</a> 
                                        <a class="redirect-link" href="${pageContext.request.contextPath}/paths/deletePath/${path.id}">delete</a>
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