<html>
    <head>
        <%@include file = "parts/includes.jsp" %>
        <title>Station management</title>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.listen.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $('#confirmButton').click(processSaving);

                $('#bindRelationButton').click(function() {
                    var currentStationId = $('#currentStationId').attr("value");
                    var currentStationName = $('#currentStationName').attr("value");
                    var nextStationId = $('#next-station-select option:selected').val();
                    var nextStationName = $('#next-station-select option:selected').text();
                    var cost = $('#cost').val();
                    $('#next-station-select option:selected').remove();
                    var relationsToAppend =
                            "<tr><td class='first-station serializable-column' value='" + currentStationId + "'>" + currentStationName + "</td>\n\
                                <td class='second-station serializable-column' value='" + nextStationId + "'>" + nextStationName + "</td>\n\
                                <td class='cost-column serializable-column'>" + cost + "</td>\n\
                                <td><a class='deleteRelationButton' href='#' onclick='return false' \n\
                                first-station='" + currentStationName + "' second-station='" + nextStationName + "'>delete</a></td></tr>"
                    relationsToAppend = relationsToAppend +
                            "<tr><td class='first-station serializable-column' value='" + nextStationId + "'>" + nextStationName + "</td>\n\
                                 <td class='second-station serializable-column' value='" + currentStationId + "'>" + currentStationName + "</td>\n\
                                 <td class='cost-column serializable-column'>" + cost + "</td>\n\
                                 <td><a class='deleteRelationButton' href='#' onclick='return false' \n\
                                 first-station='" + currentStationName + "' second-station='" + nextStationName + "'>delete</a></td></tr>";
                    $("#relation-table > tbody").append(relationsToAppend);
                });

                $('.redirect-link').click(function() {
                    $.ajax({
                        url: this,
                        type: 'GET',
                        success: function(data) {
                            window.location.replace("${pageContext.request.contextPath}/stations");
                        }
                    });
                })
                
                $('.edit-station-link').click(function() {
                    $.ajax({
                        url: this,
                        type: 'GET',
                        success: function(data) {
                            success(data);
                        }
                    });
                })

                $.listen("click", ".deleteRelationButton", function() {
                    var firstStation = $(this).attr("first-station");
                    var secondStation = $(this).attr("second-station");
                    var currentStationName = $('#currentStationName').attr("value");
                    $("#relation-table tr").each(function() {
                        
                        if ($('td.first-station', $(this)).text() == firstStation &&
                            $('td.second-station', $(this)).text() == secondStation) {
                                if ($('td.first-station', $(this)).text() == currentStationName) {
                            $('#next-station-select').append("<option value='" + $('td.second-station', $(this)).attr("value") + "'>"
                                                            + $('td.second-station', $(this)).text() + "</option>");
                        } else if ($('td.second-station', $(this)).text() == currentStationName) {
                            $('#next-station-select').append("<option value='" + $('td.first-station', $(this)).attr("value") + "'>"
                                                            + $('td.first-station', $(this)).text() + "</option>");
                        }
                                $(this).remove();
                        } else
                        if ($('td.first-station', $(this)).text() == secondStation &&
                                $('td.second-station', $(this)).text() == firstStation) {
                            $(this).remove();
                        }
                        
                    });
                });
            });

            function success(data) {
                $("#stationName").val(data.station.name);
                $('#currentStationId').val(data.station.id);
                $('#currentStationName').val(data.station.name);
                var relationTable = "<tr class='table-header'>\n\
                                        <th width='150px'>Current station</th>\n\
                                        <th width='150px'>Next station</th>\n\
                                        <th width='100px'>Cost</th>\n\
                                        <th width='150px'>Actions</th></tr>";
                var nextStationSelect = '';
                for (var i in data.relationList) {
                    if (data.relationList[i].id == null) {
                        data.relationList[i].id = '';
                    }
                    relationTable = relationTable
                            + "<tr><td class='first-station serializable-column' value='" + data.relationList[i].currentStationId + "'>" + data.relationList[i].currentStationName + "</td>\n"
                            + "<td class='second-station serializable-column' value='" + data.relationList[i].nextStationId + "'>" + data.relationList[i].nextStationName + "</td>\n"
                            + "<td class='cost-column serializable-column'>" + data.relationList[i].cost + "</td>\n"
                            + "<td><a class='deleteRelationButton' href='#' onclick='return false' \n\
                                    first-station='" + data.relationList[i].currentStationName + "' second-station='" + data.relationList[i].nextStationName + "'>delete</a></td></tr>"
                }
                for (var i in data.nextStationList) {
                    nextStationSelect = nextStationSelect
                            + "<option value=\"" + data.nextStationList[i].id + "\"> "
                            + data.nextStationList[i].name + "</option>"
                }
                $("#next-station-select").html(nextStationSelect);
                $("#relation-table").html(relationTable);
            }

            function processSaving() {
                var stationId = $('#currentStationId').attr("value");
                var stationName = $('#currentStationName').attr("value");
                var newStationName = $('#stationName').val();
                var relationTableArray = [];
                $('#relation-table tr').each(function() {
                    if ($(this).attr("class") != 'table-header') {
                        var rowData = {
                            currentStationId: $('td.first-station', $(this)).attr("value"),
                            currentStationName: $('td.first-station', $(this)).text(),
                            nextStationId: $('td.second-station', $(this)).attr("value"),
                            nextStationName: $('td.second-station', $(this)).text(),
                            cost: $('td.cost-column', $(this)).text(),
                        }
                        relationTableArray.push(rowData);
                    }
                });
                var stationFormDTO = {
                    station: {
                        id: stationId,
                        name: stationName,
                    },
                    newStationName: newStationName,
                    nextStationList: null,
                    relationList: relationTableArray,
                };
                $.ajax({
                        headers: { 
                            'Accept': 'application/json',
                            'Content-Type': 'application/json' 
                        },
                        url: '${pageContext.request.contextPath}/stations/addStation',
                        type: 'POST',
                        data: JSON.stringify(stationFormDTO),
                        success: function(data) {
                            window.location.replace("${pageContext.request.contextPath}/stations");
                        }
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
                    <h2>Station:</h2>
                    Name:
                    <input id="stationName" type="text" value="${station.name}"/>
                    <div id="relations">
                        <%--<c:if test="${!empty relationList}">--%>
                        <h3>Relations:</h3>
                        <table id="relation-table" class="table-entity-list">
                            <tr class="table-header">
                                <th width="150px">Current station</th>
                                <th width="150px">Next station</th>
                                <th width="100px">Cost</th>
                                <th width="150px">Actions</th>
                            </tr>
                        </table>
                        <%--</c:if>--%>
                    </div>

                    <h3>Bind new relation:</h3>
                    <form method="post">
                         <%--///--%>
                        <input type="hidden" id="currentStationId" value="${station.id}"/>
                        <input type="hidden" id="currentStationName" value="${station.name}"/>
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
                                <td>Cost: </td> 
                                <td><input type="text" id="cost"/></td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <input id="bindRelationButton" type="button" value="Bind relation"/>
                                </td>
                            </tr>
                        </table>  
                    </form>

                    <form>
                        <p><input id="confirmButton" type="button" value="Confirm"></p>
                    </form>

                    <h3>Existing stations:</h3>

                    <c:if  test="${!empty stationList}">
                        <table class="table-entity-list">
                            <tr>
                                <th width="100px">id</th>
                                <th width="200px">Name</th>
                                <th width="200px">Actions</th>
                            </tr>
                            <tbody id="station-table">
                            <c:forEach items="${stationList}" var="station">
                                <tr>
                                    <td>${station.id}</td>
                                    <td>${station.name}</td>
                                    <td><a class="edit-station-link" href="${pageContext.request.contextPath}/stations/editStation/${station.id}" onclick="return false">edit</a> 
                                        <a href="${pageContext.request.contextPath}/stations/deleteStation/${station.id}" >delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                    <div class="operation-msg">${msg}</div>
                    <div class="operation-error">${error}</div>
                </div>
            </div>
        </div>
        <%@include file = "parts/footer.jsp" %>
    </body>

</html>
