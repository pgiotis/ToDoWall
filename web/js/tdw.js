//=============================================================================
//ToDo Wall project created by Panagiotis Giotis <giotis.p@gmail.com>
//=============================================================================


//drag and drop event
//=============================================================================
function allowDrop(ev) {
    ev.preventDefault();
}

function drag(ev) {
    ev.dataTransfer.setData("Text", ev.target.id);
}

function drop(ev) {
    ev.preventDefault();
    var data = ev.dataTransfer.getData("Text");
    var element = document.getElementById(data); 
    ev.target.appendChild(element);
    element.setAttribute("ElementType", ev.target.id); 
}
function uid() {
    var datetime = new Date();
    var id = datetime.getTime();
    return id;
}
//=============================================================================



function GetURLParameter(sParam) {
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++)
    {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam)
        {
            return sParameterName[1];
        }
    }
}


/**
 * add new item on the wall 
 */
function add() {
    var id = uid();
    $("#todo").append('<a href="#" class="list-group-item"' +
            ' id="' + id + '" draggable="true" ondragstart="drag(event)" ' +
            'ElementDescription="' + $("#InputDescription").val() + '"' +
            'ElementTitle="' + $("#InputTitle").val() + '"' +
            'ElementType="todo"' +
            ' >' +
            '<span class="btn glyphicon glyphicon-remove" onclick="RemoveItem(\'' +
            id + '\')"></span>' +
            $("#InputDescription").val() + '<span class="badge">' +
            $("#InputTitle").val() + '</span></a>');
    $("#InputTitle").val("");
    $("#InputDescription").val("");
}


/**
 * Save current state of the wall 
 */
function save() {
    var username = GetURLParameter("username");
    var Cdata = "{";
    //For each TODO:
    var index=0;
    $('.list-group-item').each(function() {
        index++;
        Cdata = Cdata +"\""+ index+"\":"+"\""+$(this).attr("ElementType")+"~" + $(this).attr("ElementTitle") + "~" +
                $(this).attr("ElementDescription") + "\",";

    });

    Cdata = Cdata.slice(0, -1) + "}";

    $.ajax({
        type: "GET",
        url: "http://"+$(location).attr('host')+"/ToDoWall/saveToDo?username=" + username +
                "&data=" + Cdata
    }).done(function(data) {
        if (data.saved === "1") {
            alert("data saved");
        } else {
            alert("data not saved")
        }
    });

}


/**
 * Remove a item from the wall 
 * @param {type} id items id
 */
function RemoveItem(id) {
    $('#' + id).remove();
}


/**
 * Load all saved ToDo items on the wall 
 */
function load() {
    var username = GetURLParameter("username");
    $.ajax({
        type: "GET",
        url: "http://"+$(location).attr('host')+"/ToDoWall/getToDo?username=" + username
    }).done(function(data) {
        $.each(data, function(key,val) {
            var id = uid();
            var arr = val.split('~');
            $("#"+arr[0]).append('<a href="#" class="list-group-item"' +
                    ' id="' + id + '" draggable="true" ondragstart="drag(event)" ' +
                    'ElementDescription="' + arr[2] + '"' +
                    'ElementTitle="' + arr[1] + '"' +
                    'ElementType="' + key + '"' +
                    ' >' +
                    '<span class="btn glyphicon glyphicon-remove" onclick="RemoveItem(\'' +
                    id + '\')"></span>' +
                    arr[2] + '<span class="badge">' +
                    arr[1] + '</span></a>');
        });
    });
}


/**
 * Check user credentials on ToDO wall
 * @returns 
 */
function checkLogin() {
    $.ajax({
        type: "GET",
        url: "http://"+$(location).attr('host')+"/ToDoWall/checklogin?username="
                + document.getElementById("Username").value +
                "&pass=" + document.getElementById("Password").value
    }).done(function(data) {
        if (data.valid === "1") {
            $(".form-signin").attr("action", "home.html?username=" + document.getElementById("Username").value
                    + "&pass=" + document.getElementById("Password").value);
            document.getElementById("hiddenButton").click();
        } else {
            alert("Wrong credentials")
            document.getElementById("Username").value = "";
            document.getElementById("Password").value = "";
        }
    });
}


/**
 * register user on ToDO wall
 * @returns 
 */
function register() {
    $.ajax({
        type: "GET",
        url: "http://"+$(location).attr('host')+"/ToDoWall/register?username="
                + document.getElementById("RegisterUsername").value +
                "&pass=" + document.getElementById("RegisterPassword").value
    }).done(function(data) {
        if (data.registered === "1") {
            alert("User registered");
            document.getElementById("RegisterUsername").value = "";
            document.getElementById("RegisterPassword").value = "";
            $('#myModal').modal('hide')
        } else {
            alert("User exists");
            document.getElementById("RegisterUsername").value = "";
            document.getElementById("RegisterPassword").value = "";
        }
    });
}