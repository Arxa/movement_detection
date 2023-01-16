var webSocket;
var mp3_url = './alarm_sound.mp3';
var audio = new Audio(mp3_url);
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

