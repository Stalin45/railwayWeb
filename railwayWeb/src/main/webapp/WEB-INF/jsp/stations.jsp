<html>
    <head>
        <%@include file = "parts/includes.jsp" %>
        <title>Station management</title>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.listen.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/stations.js"></script>
    </head>

    <body> 
        <div class="wrapper">
            <%@include file = "parts/header.jsp" %>
            <div class="middle">
                <%@include file = "parts/sidebar.jsp" %>
                <div class="container">
                    <table class="controll-table">
                        <tr>
                            <td>
                                <h3>Station:</h3>
                                Name: <input class="form-control" placeholder="station name" id="stationName" type="text" />
                            </td>
                        </tr>
                        <tr>
                            <td>
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
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <h3>Bind new relation:</h3>
                                <form method="post">
                                    <%--///--%>
                                    <input type="hidden" id="currentStationId" value="${station.id}"/>
                                    <input type="hidden" id="currentStationName" value="${station.name}"/>
                                    <table>
                                        <tr>
                                            <td>Next station: </td>
                                            <td><select id="next-station-select" class="form-control" items="${nextStationList}" >
                                                    <c:forEach items="${nextStationList}" var="nStation">
                                                        <option value="${nStation.id}">${nStation.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Cost: </td> 
                                            <td><input type="text" id="cost" class="form-control"/></td>
                                        </tr>
                                        <tr>
                                            <td colspan="2" class="cell-with-button">
                                                <input id="bindRelationButton" type="button" class="btn btn-large" value="Bind relation"/>
                                            </td>
                                        </tr>
                                    </table>  
                                </form>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <form>
                                    <p><input id="confirmButton" class="btn btn-primary" type="button" value="Confirm"></p>
                                </form>
                            </td>
                        </tr>
                    </table><br/>
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
