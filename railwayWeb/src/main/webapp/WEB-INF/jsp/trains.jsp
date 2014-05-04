<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <%@include file = "parts/includes.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=utf8">
        <title>Train management</title>
        <script type ="text/javascript">
            $(document).ready(function() {
                $('#confirmButton').click(processSaving);

                $('.ajaxlink').click(function() {
                    $.ajax({
                        url: this,
                        type: 'GET',
                        success: function(data) {
                            success(data);
                        }
                    });
                });
                
                $('.overlay-bg').hide();
                
                $('.show-popup').click(function(event) {
                    event.preventDefault();
                    var uri = this.toString();
                    var id = uri.substring(uri.lastIndexOf("/") + 1);
                    $.ajax({
                        url: this,
                        type: 'GET',
                        success: function(data) {
                            if (uri.indexOf("describeTrainType") != -1) {
                                overlayTrainTypes(data, id);
                            }
                            if (uri.indexOf("describePath") != -1) {
                                overlayPaths(data, id);
                            }
                            if (uri.indexOf("viewPassengers") != -1) {
                                overlayPassengers(data, id);
                            }
                        }
                    });
                    var docHeight = $(document).height(); 
                    var scrollTop = $(window).scrollTop(); //grab the px value from the top of the page to where you're scrolling      
                    $('.overlay-bg').show().css({'height': docHeight}); //display your popup and set height to the page height
                    $('.overlay-content').css({'top': scrollTop + 20 + 'px'}); //set the content 20px from the window top  
                });

                $('.close-btn').click(function() {
                    $("#overlay-header").html('');
                    $("#overlay-table").html('');
                    $('.overlay-bg').hide(); // hide the overlay
                });

                $('.overlay-bg').click(function() {
                    $("#overlay-header").html('');
                    $("#overlay-table").html('');
                    $('.overlay-bg').hide();
                })
                
                $('.overlay-content').click(function() {
                    return false;
                });
            });

            function success(data) {
                if (data.id == null) {
                    $("#train-header").html("<h3>Train: new train</h3>");
                } else {
                    $("#train-header").html("<h3>Train: " + data.id + "</h3>");
                }
                $("#train-id").val(data.id);
                var date = new Date(data.date);
                var y = date.getFullYear();
                var m = date.getMonth() + 1;
                var d = date.getDate;
                if (m < 10)
                    m = "0" + m;
                if (d < 10)
                    d = "0" + d;
                var newDate = y + "-" + m + "-" + d;
                $("#train-type-select").val(data.trainTypeId);
                $("#path-select").val(data.pathId);
                $("#date").val(newDate);
                $("#free-seats-label").html("<td colspan=\"2\">Free seats: " + data.freeSeats + "</td>");
            }
            
            function overlayTrainTypes(data, id) {
                var overlayHeader = "<h3>Train type: " + id + "</h3>";
                var tableHeader = "<tr><th width=\"100px\">id</th>\n\
                                              <th width=\"200px\">Type</th>\n\
                                              <th width=\"200px\">Cost multiplier</th>\n\
                                              <th width=\"200px\">Max seats</th>\n";
                var tableBody = "<tr><td>" + data.id + "</td>\n"
                            + "<td>" + data.type + "</td>\n"
                            + "<td>" + data.costMultiplier + "</td>\n"
                            + "<td>" + data.maxSeats + "</td></tr>";
                $("#overlay-header").html(overlayHeader);
                $("#overlay-table").html(tableHeader + tableBody);
            }
            
            function overlayPaths(data, id) {
                var overlayHeader = "<h3>Path: " + id + "</h3>";
                var tableHeader = "<tr><th width=\"10%\">Number</th>\n\
                                <th width=\"20%\">Station</th>\n\
                                <th width=\"20%\">Time</th></tr>\n";
                var tableBody = ''
                for (var i in data) {
                    var time = new Date(data[i].time);
                    var h = time.getHours();
                    var m = time.getMinutes();
                    var tableBody = tableBody + 
                            "<tr><td>" + data[i].number + "</td>\n\
                             <td>" + data[i].stationName + "</td>\n\
                             <td>" + h + ":" + m + "</td></tr>";
                }
                $("#overlay-header").html(overlayHeader);
                $("#overlay-table").html(tableHeader + tableBody);
            }
            
            function overlayPassengers(data, id) {
                var overlayHeader = "<h3>Train: " + id + "</h3>";
                var tableHeader = "<tr><th width=\"15%\">Ticket id</th>\n\
                                   <th width=\"10%\">Total cost</th>\n\
                                   <th width=\"20%\">Name</th>\n\\n\
                                   <th width=\"20%\">Second name</th>\n\\n\
                                   <th width=\"10%\">Passport</th>\n\
                                   </tr>\n";
                var tableBody = '';
                for (var i in data) {
                    tableBody = tableBody +
                                  "<tr><td>" + data[i].ticketId + "</td>\n\
                                       <td>" + data[i].totalCost + "</td>\n\
                                       <td>" + data[i].name + "</td>\n\
                                       <td>" + data[i].secondName + "</td>\n\
                                       <td>" + data[i].passport + "</td>\n\
                                   </tr>";
                }
                $("#overlay-header").html(overlayHeader);
                $("#overlay-table").html(tableHeader + tableBody);
            }

            function processSaving() {
                var trainId = $('#train-id').val();
                var trainTypeId = $('#train-type-select option:selected').val();
                var trainTypeType = $('#train-type-select option:selected').text();
                var pathId = $('#path-select option:selected').val();
                var date = $("#date").val();
                $.post('${pageContext.request.contextPath}/trains/addTrain',
                        {
                            id: trainId,
                            trainTypeId: trainTypeId,
                            trainTypeType: trainTypeType,
                            pathId: pathId,
                            dateString: date
                        },
                function(data) {
                    window.location.replace("${pageContext.request.contextPath}/trains")
                });
            }
        </script>
    </head>

    <body> 
        <div class="wrapper">
            <%@include file = "parts/header.jsp" %>
            <div class="middle">
                <%@include file = "parts/sidebar.jsp" %>
                <div class="container">
                    <div id="train-header">
                        <h3>Train: <c:if test="${train.id == null}">new train</c:if>
                            <c:if test="${train.id ne null}">${train.id}</c:if>
                            </h3>
                        </div>
                        <form method="post" action="${pageContext.request.contextPath}/trains/addTrain">
                        <input type="hidden" id="train-id" value="${train.id}"/>
                        <table>
                            <tr>
                                <td>Train type: </td>
                                <td><select id="train-type-select" items="${trainTypeList}" >
                                        <c:forEach items="${trainTypeList}" var="trainType">
                                            <option value="${trainType.id}">${trainType.type}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>Path: </td>
                                <td><select id="path-select" items="${pathList}" >
                                        <c:forEach items="${pathList}" var="path">
                                            <option value="${path.id}">${path.id}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <input type="date" id="date" value="${train.date}"/>
                                </td> 
                            </tr>
                            <tr id="free-seats-label">
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <input id="confirmButton" type="button" value="Confirm"/>
                                </td>
                            </tr>
                        </table>  
                    </form>

                    <h3>Existing train types:</h3>

                    <c:if  test="${!empty trainList}">
                        <table class="table-entity-list">
                            <tr>
                                <th width="100px">id</th>
                                <th width="200px">Type</th>
                                <th width="100px">Path</th>
                                <th width="100px">Date</th>
                                <th width="100px">Free seats</th>
                                <th width="250px">Actions</th>
                            </tr>
                            <c:forEach items="${trainList}" var="train">
                                <tr>
                                    <td>${train.id}</td>
                                    <td><a class="show-popup" href="${pageContext.request.contextPath}/trains/describeTrainType/${train.trainTypeId}">${train.trainTypeType}</a></td>
                                    <td><a class="show-popup" href="${pageContext.request.contextPath}/trains/describePath/${train.pathId}">${train.pathId}</a></td>
                                    <td><fmt:formatDate pattern="dd.MM.yyyy" value="${train.date}" /></td>
                                    <td>${train.freeSeats}</td>
                                    <td><a class="ajaxlink" href="${pageContext.request.contextPath}/trains/editTrain/${train.id}" onclick="return false">edit</a> 
                                        <a href="${pageContext.request.contextPath}/trains/deleteTrain/${train.id}">delete</a><br/>
                                        <a class="show-popup" href="${pageContext.request.contextPath}/trains/viewPassengers/${train.id}" onclick="return false">view passengers</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:if>
                </div>
            </div>
        </div>
        <%@include file = "parts/footer.jsp" %>
        <div class="overlay-bg">
            <div class="overlay-content">
                <div id="overlay-header"></div>
                <div id="overlay-body">
                    <table id="overlay-table" class="table-entity-list"></table>
                </div>
                <div id="overlay-footer">
                    <button class="close-btn">Close</button>
                </div>
            </div>
        </div>
    </body>

</html>
