import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class GameA {
    private static final String QUEUE_NAME = "game_queue";

    public static void main(String[] args) throws Exception {
        // Create ship object
        Ship ship = new Ship("Fighter3000", 50, 10, 5);

        // Send the ship object as flat file data using RabbitMQ
        sendShipViaRabbitMQ(ship);

        // Send the ship object as a serialized JSON message using a Web Service
        sendShipViaWebService(ship);
    }

    private static void sendShipViaRabbitMQ(Ship ship) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String flatFileData = ship.getName() + " " + ship.getSpeed() + " " + ship.getDamage() + " " + ship.getDefense();
            channel.basicPublish("", QUEUE_NAME, null, flatFileData.getBytes(StandardCharsets.UTF_8));
            System.out.println("[x] Sent '" + flatFileData + "'"); // Print the flat file data
        }
    }

    private static void sendShipViaWebService(Ship ship) throws Exception {
        String jsonData = "{\"name\":\"" + ship.getName() + "\",\"speed\":" + ship.getSpeed() + ",\"damage\":" + ship.getDamage() + ",\"defense\":" + ship.getDefense() + "}";
        System.out.println("Game object serialized to json string: " + jsonData); // Print the JSON string

        Thread serverThread = new Thread(() -> {
            try {
                HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
                server.createContext("/sendShip", new ShipHandler(jsonData));
                server.setExecutor(null); // Use the default executor
                server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverThread.start();
    }

    static class ShipHandler implements HttpHandler {
        private final String jsonData;

        ShipHandler(String jsonData) {
            this.jsonData = jsonData;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(200, jsonData.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(jsonData.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                exchange.getResponseBody().close();
            }
        }
    }
}









