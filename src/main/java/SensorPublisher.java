import org.eclipse.paho.client.mqttv3.*;
import java.util.Random;

public class SensorPublisher {

    public static void main(String[] args) throws Exception {

        MqttClient client = new MqttClient("tcp://localhost:1883", "SensorPublisher");
        client.connect();

        Random random = new Random();

        while (true) {

            int temp = 18 + random.nextInt(15);
            int humidity = 40 + random.nextInt(30);
            boolean smoke = random.nextInt(20) == 1;

            client.publish("building/room1/temperature",
                    new MqttMessage(("Temp=" + temp).getBytes()));

            client.publish("building/room1/humidity",
                    new MqttMessage(("Humidity=" + humidity).getBytes()));

            client.publish("building/room1/smoke",
                    new MqttMessage(("Smoke=" + smoke).getBytes()));

            Thread.sleep(3000);
        }
    }
}
