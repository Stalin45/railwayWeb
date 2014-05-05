<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <%@include file = "parts/includes.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=utf8">
        <title>Buy ticket</title>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/tickets.js"></script>
    </head>

    <body> 
        <div class="wrapper">
            <%@include file = "parts/header.jsp" %>
            <div class="middle">
                <%@include file = "parts/sidebar.jsp" %>
                <!--<input class=\"buybutton\" type=\"button\" value=\"Buy ticket\"/>-->
                <div class="container">
                    <h2>Choose a train:</h2>
                    <table>
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
                                    <td><a class="show-popup" href="${pageContext.request.contextPath}/tickets/orderTrain/${train.id}">Buy ticket</a></td>
                                </tr>
                            </c:forEach>
                        </table>
                    </table>
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
                <div class="operation-msg"></div>
                <div class="operation-error"></div>
                <div class="overlay-footer">
                    <button class="close-btn btn btn-large">Close</button>
                </div>
            </div>
        </div>
    </body>

</html>

