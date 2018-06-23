var webSocket;

window.onload = function() {
    alert ('hello world');
};

$(document).ready(function(){
    webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/socket");
    //webSocket.onopen  = function(){ alert("websocket.onopen"); };
    // webSocket.onopen  = function(){ webSocket.send("web message"); };
    webSocket.onmessage = function (msg) {onMessage(msg);};
});


function onMessage(msg) {
    var encodedImage = JSON.parse(msg.data).encodedImage;
    $("#frame").attr("src", encodedImage);
}

