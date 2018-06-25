import spark.Spark;

public class Main {

    public static void main(String[] args) throws Exception {
        Utils.loadNatives();
        Camera.openCamera();
        //Spark.port(8080);
        Spark.staticFiles.location("/web");
        Spark.staticFiles.expireTime(600);
        // websocket path should be the same as the one defined in webSocket.js
        Spark.webSocket("/socket", WsHandler.class);
        Spark.init();
    }
}
