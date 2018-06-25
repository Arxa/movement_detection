import spark.Spark;

public class Main {

    public static void main(String[] args){
        Utils.loadNatives();
        Camera.openCamera();
        Spark.staticFiles.location("/web");
        Spark.staticFiles.expireTime(600);
        // websocket path should be the same as the one defined in webSocket.js
        Spark.webSocket("/socket", WsHandler.class);
        Spark.init();
    }
}
