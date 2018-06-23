import spark.Spark;

public class Main {

    public static void main(String[] args){
        Utils.loadNatives();
        Camera.openCamera();
        Spark.staticFiles.location("/web");
        Spark.staticFiles.expireTime(600);
        Spark.webSocket("/socket", WsHandler.class);
        Spark.init();

//        get("/",(request, response) ->
//                "Welcome"
//        );
    }
}
