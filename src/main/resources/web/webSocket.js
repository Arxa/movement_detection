var webSocket;

$(document).ready(function(){
    webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/socket");
    webSocket.onmessage = function (msg) {onMessage(msg);};
    // above event will be triggered by 'WsHandler.broadcastMessage()'
});

function onMessage(msg) {
    var encodedImage = msg.data; // get websocket's data
    $("#frame").attr("src", encodedImage); // shows the image in index.html
}

