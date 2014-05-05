<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <%@include file = "parts/includes.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=utf8">
        <title>Train management</title>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/trains.js"></script>
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
                                <div id="train-header">
                                    <h3>Train: <c:if test="${train.id == null}">new train</c:if>
                                        <c:if test="${train.id ne null}">${train.id}</c:if>
                                        </h3>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <form method="post" action="${pageContext.request.contextPath}/trains/addTrain">
                                    <input type="hidden" id="train-id" value="${train.id}"/>
                                    <table>
                                        <tr>
                                            <td>Train type: </td>
                                            <td><select id="train-type-select" class="form-control" items="${trainTypeList}" >
                                                    <c:forEach items="${trainTypeList}" var="trainType">
                                                        <option value="${trainType.id}">${trainType.type}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Path: </td>
                                            <td><select id="path-select" class="form-control" items="${pathList}" >
                                                    <c:forEach items="${pathList}" var="path">
                                                        <option value="${path.id}">${path.id}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Date: </td>
                                            <td>
                                                <input type="date" id="date" class="form-control" value="${train.date}"/>
                                            </td> 
                                        </tr>
                                        <tr id="free-seats-label">
                                        </tr>
                                    </table>  
                                </form>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input id="confirmButton" type="button" class="btn btn-primary" value="Confirm"/>
                            </td>
                        </tr>
                    </table><br/>
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
                    <button class="close-btn btn btn-large">Close</button>
                </div>
            </div>
        </div>
    </body>

</html>
