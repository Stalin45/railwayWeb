<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <%@include file = "parts/includes.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=utf8">
        <title>Find trains</title>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/findTrains.js"></script>
    </head>

    <body> 
        <div class="wrapper">
            <%@include file = "parts/header.jsp" %>
            <div class="middle">
                <%@include file = "parts/sidebar.jsp" %>
                <div class="container">
                    <h2>Find trains:</h2><br/>
                    <table class="controll-table">
                        <tr>
                            <td alegn="left">Station from: </td>
                            <td><input class="form-control tags" placeholder="from" id="station-from" type="text"/></td>
                            <td rowspan="2" class = ".filter">Filter:</td> 
                            <td rowspan="2" align="left">
                                <input type="radio" name="filter" value="stations" checked>Stations<br/>
                                <input type="radio" name="filter" value="date">Date<br/>
                                <input type="radio" name="filter" value="stationsDate">Stations/date
                            </td>
                        </tr>
                        <tr>
                            <td align="left">Station To: </td>
                            <td><input class="form-control tags" placeholder="to" id="station-to" type="text"/></td>
                        </tr>
                        <tr>
                            <td alegn="left">Date: </td>
                            <td><input type="date" id="date" class="form-control" value="${train.date}"/></td>
                            <td colspan="2" align="center" ><input id="find-button" class="btn btn-large" type="button" value="Find"/></td>
                        </tr>
                        <tr>
                            <table id="found-trains-table" class="table-entity-list"></table>
                        </tr>
                        <tr>
                            <div class="operation-msg"></div>
                            <div class="operation-error"></div>
                        </tr>
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
