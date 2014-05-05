$(document).ready(function() {

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
                if (uri.indexOf("orderTrain") != -1) {
                    overlayTicket(data, id);
                }
            }
        });
        var docHeight = $(document).height();
        var scrollTop = $(window).scrollTop(); //grab the px value from the top of the page to where you're scrolling      
        $('.overlay-bg').show().css({'height': docHeight}); //display your popup and set height to the page height
        $('.overlay-content').css({'top': scrollTop + 20 + 'px'}); //set the content 20px from the window top  
    });

    $('.close-btn').click(function() {
        $(".overlay-header").html('');
        $(".overlay-table").html('');
        $(".operation-msg").html('');
        $(".operation-error").html('');
        $('.overlay-bg').hide(); // hide the overlay
    });

    $('.overlay-bg').click(function() {
        $(".overlay-header").html('');
        $(".overlay-table").html('');
        $(".operation-msg").html('');
        $(".operation-error").html('');
        $('.overlay-bg').hide();
    })

    $('.overlay-content').click(function() {
        return false;
    });
});

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

function overlayTicket(data, id) {
    var overlayHeader = "<h3>Please, fill the information about passenger:</h3>";

    var tableBody = "<tr><td>Train: </td><td id=\"ticket-train\">" + id + "</td></tr>\n\
                                 <tr><td id=\"ticket-name\">Name: </td><td><input id=\"name\" type=\"text\"/></td></tr>\n\
                                 <tr><td id=\"ticket-sec-name\">Second name: </td><td><input id=\"second-name\" type=\"text\"/></td></tr>\n\
                                 <tr><td id=\"ticket-passport\">Passport: </td><td><input id=\"passport\" type=\"text\"/></td></tr>\n\
                                 <tr><td colspan=\"2\" ><a id=\"ajaxlink\" href=\"/railwayWeb/tickets/buyTicket\" \n\
                                onclick=\"return false\">Buy ticket</a></td></tr>"
    $("#overlay-header").html(overlayHeader);
    $("#overlay-table").html(tableBody);
    $('#ajaxlink').bind('click', processBuying);
}

function processBuying() {
    var trainId = $("#ticket-train").text();
    var name = $("#name").val();
    var secondName = $("#second-name").val();
    var passport = $("#passport").val();
    $.ajax({
        url: this,
        type: 'POST',
        data: ({
            trainId: trainId,
            name: name,
            secondName: secondName,
            passport: passport
        }),
        success: function(data) {
            $(".operation-msg").html(data.msg);
            $(".operation-error").html(data.error);
        }
    });
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