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
    var tableHeader = "<tr><th width=\"100px\">Ticket id</th>\n\
                                   <th width=\"150px\">Name</th>\n\\n\
                                   <th width=\"150px\">Second name</th>\n\\n\
                                   <th width=\"100px\">Passport</th>\n\
                                   </tr>\n";
    var tableBody = '';
    for (var i in data) {
        tableBody = tableBody +
                "<tr><td>" + data[i].ticketId + "</td>\n\
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
    $.post('/railwayWeb/trains/addTrain',
            {
                id: trainId,
                trainTypeId: trainTypeId,
                trainTypeType: trainTypeType,
                pathId: pathId,
                dateString: date
            },
    function(data) {
        window.location.replace("/railwayWeb/trains")
    });
}