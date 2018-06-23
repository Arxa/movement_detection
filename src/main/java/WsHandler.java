import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

@WebSocket
public class WsHandler {

    private Session session;

    @OnWebSocketConnect
    public void connected(Session session){
        System.out.println("connected");
        this.session = session;
        startService();
    }

    @OnWebSocketClose
    public void close(int statusCode, String reason) {
        System.out.println("close");
        this.session = null;
    }

    @OnWebSocketMessage
    public void message(String message) throws IOException {
        System.out.println("message detected: " + message);
    }

    private void startService() {
        Timer timer = new Timer("myTimer", true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                broadcastMessage();
            }
        }, 10, 10);
    }

    private void broadcastMessage() {
        System.out.println("broadcasting...");
        try {
            session.getRemote().sendString(
                    String.valueOf(new JSONObject().put("encodedImage", Camera.encodeWebcamFrame())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
