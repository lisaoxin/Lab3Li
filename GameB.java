import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GameB {
    private static final String QUEUE_NAME = "game_queue";
    private static final String WEB_SERVICE_URL = "http://localhost:8000/sendShip";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        // Receive the game object as a flat file data from RabbitMQ
        receiveGameViaRabbitMQ();

        // Receive the game object as a serialized JSON message from a Web Service
        receiveGameViaWebService();
    }

    /**
     * Description: Receives a game object as a flat file data from RabbitMQ
     */
    private static void receiveGameViaRabbitMQ() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("[x] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String flatFileData = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("[x] Received '" + flatFileData + "'"); // Print the flat file data
            Ship ship = convertFlatFileToShip(flatFileData);
            processShipData(ship); // Process the received ship data
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }

    /**
     * Description: Receives a game object as a serialized JSON message from a Web Service
     */
    private static void receiveGameViaWebService() {
        try {
            URL url = new URL(WEB_SERVICE_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            int responseCode = con.getResponseCode();
            System.out.println("\nResponse Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String jsonData = in.readLine();
                in.close();

                System.out.println("Response: " + jsonData); // Print the JSON string

                Ship ship = convertJSONToShip(jsonData);
                processShipData(ship); // Process the received ship data

                System.out.println("\nGame object deserialized from JSON string:");
                System.out.println("Ship type: " + ship.getName());
                System.out.println("Speed: " + ship.getSpeed());
                System.out.println("Damage: " + ship.getDamage());
                System.out.println("Defense: " + ship.getDefense());
            } else {
                System.out.println("Failed to receive game via Web Service: HTTP error code " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Description: Processes the received ship data
     * @param ship The Ship object received from Game A
     */
    private static void processShipData(Ship ship) {
        System.out.println("\nProcessing ship data:");
        System.out.println("Ship type: " + ship.getName());
        System.out.println("Speed: " + ship.getSpeed());
        System.out.println("Damage: " + ship.getDamage());
        System.out.println("Defense: " + ship.getDefense());
        // Add your game logic here to process the received ship data
    }

    /**
     * Description: Converts a flat file data to a Ship object
     * @param flatFileData The flat file data received from RabbitMQ
     * @return The Ship object
     */
    private static Ship convertFlatFileToShip(String flatFileData) {
        String[] parts = flatFileData.split(" ");
        String name = parts[0];
        int speed = Integer.parseInt(parts[1]);
        int damage = Integer.parseInt(parts[2]);
        int defense = Integer.parseInt(parts[3]);
        return new Ship(name, speed, damage, defense);
    }

    /**
     * Description: Converts a JSON string to a Ship object
     * @param jsonData The JSON string received from the Web Service
     * @return The Ship object
     */
    private static Ship convertJSONToShip(String jsonData) throws IOException {
        return objectMapper.readValue(jsonData, Ship.class);
    }
}





































