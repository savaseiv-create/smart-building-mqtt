import org.eclipse.paho.client.mqttv3.*;
import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import java.net.InetSocketAddress;

public class BackendServer {
    public static void main(String[] args) throws Exception {

        // WebSocket Server
        WebSocketServer wsServer = new WebSocketServer(new InetSocketAddress(8080)) {

            @Override
            public void onOpen(WebSocket conn, ClientHandshake handshake) {
                System.out.println("Client Web connecté");
            }
            @Override
            public void onMessage(WebSocket conn, String message) {}
            @Override
            public void onClose(WebSocket conn, int code, String reason, boolean remote) {
                System.out.println("Client Web déconnecté");
            }
            @Override
            public void onError(WebSocket conn, Exception ex) {
                ex.printStackTrace();
            }
            @Override
            public void onStart() {
                System.out.println("WebSocket serveur démarré !");
            }
        };

        wsServer.start();
        System.out.println("WebSocket lancé sur ws://localhost:8080");

        // MQTT Subscriber
        MqttClient mqttClient = new MqttClient("tcp://localhost:1883", "WebBridge");
        mqttClient.connect();

        mqttClient.subscribe("building/+/+", (topic, message) -> {
            String payload = new String(message.getPayload());
            String data = topic + "|" + payload;  // note le "|" pour index.html
            wsServer.broadcast(data);
            System.out.println(data);
        });
    }
}
