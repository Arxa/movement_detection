import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

@WebSocket
public class WsHandler {

    private Session session;

    /**
     * Runs when the page is loaded
     */
    @OnWebSocketConnect
    public void connected(Session session){
        System.out.println("Socket opened");
        this.session = session;
        startService(); // We start sending data through the websocket
    }

    /**
     * Runs when the page is closed
     */
    @OnWebSocketClose
    public void close(int statusCode, String reason) {
        System.out.println("Socket closed");
        this.session = null;
    }

    /**
     * Live streaming is achieved by repeatedly (every 10 ms) sending
     * a webcam frame through the websocket.
     */
    private void startService() {
        Timer timer = new Timer("myTimer", true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    broadcastMessage();
                } catch (IOException e) {
                    timer.cancel();
                }
            }
        }, 10, 10);
    }

    /**
     * Sends though the websocket, an image encoded as String
     */
    private void broadcastMessage() throws IOException {
        // this code will trigger the js event method 'webSocket.onmessage'
        session.getRemote().sendString(Camera.encodeWebcamFrame());
    }
}
