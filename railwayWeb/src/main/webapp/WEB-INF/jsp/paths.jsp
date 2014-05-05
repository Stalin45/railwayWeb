<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <%@include file = "parts/includes.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=utf8">
        <title>Path management</title>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.ui.timepicker.js"></script> 
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/paths.js"></script>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/jquery.ui.timepicker.css"/>
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
                                <div id="pathNumber">
                                    <h3>Path: <c:if test="${path.id == null}">new path</c:if>
                                        <c:if test="${path.id ne null}">${path.id}</c:if>
                                        </h3>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                <%--<c:if test="${!empty destinationList}">--%>
                                <h3>Destinations:</h3>
                                <table class="table-entity-list">
                                    <tr>
                                        <th width="150px">Number</th>
                                        <th width="200px">Station</th>
                                        <th width="150px">Time</th>  
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
                            </td>
                        </tr>
                        <tr> 
                            <td>
                                <h3>Push new destination to the path:</h3>
                                <form method="post">
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
                                            <td>Time: </td> 
                                            <td><input type="time" id="time" class="form-control"/></td>
                                        </tr>
                                        <tr>
                                            <td colspan="2" clas="cell-with-button" style="text-align: center">
                                                <input id="pushDestinationButton" class="btn btn-large" type="button" value="Push destination"/>
                                            </td>
                                        </tr>
                                    </table>  
                                </form>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <form method="POST" action="${pageContext.request.contextPath}/paths/addPath">
                                    <p><input id="confirmButton" class="btn btn-primary" type="Submit" value="Confirm"></p>
                                </form>
                            </td>
                        </tr>
                    </table><br/>
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
                    <div class="operation-msg">${msg}</div>
                    <div class="operation-error">${error}</div>
                </div>
            </div>
        </div>
        <%@include file = "parts/footer.jsp" %>
    </body>

</html>