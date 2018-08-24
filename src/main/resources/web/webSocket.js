var webSocket;
var audio = new Audio('./alarm_sound3.mp3');
audio.loop = false;

$(document).ready(function(){
    webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/socket");
    webSocket.onmessage = function (msg) {onMessage(msg);};
    // above event will be triggered by 'WsHandler.broadcastMessage()'
});

function onMessage(msg) {
    var data = JSON.parse(msg.data); // retrieving json data from websocket
    $("#frame").attr("src", data.image); // shows the image in index.html
    if (data.sound === true){
        audio.play();
    }
}

