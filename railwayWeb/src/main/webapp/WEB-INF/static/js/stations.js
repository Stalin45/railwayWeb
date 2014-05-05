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
                window.location.replace("/railwayWeb/stations");
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
        url: '/railwayWeb/stations/addStation',
        type: 'POST',
        data: JSON.stringify(stationFormDTO),
        success: function(data) {
            window.location.replace("/railwayWeb/stations");
        }
    });
}