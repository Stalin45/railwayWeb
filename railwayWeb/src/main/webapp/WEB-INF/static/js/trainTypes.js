$(document).ready(function() {

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
        var h = time.getHours();
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