import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

//GameB class
public class GameB {
    private static final String RABBITMQ_QUEUE_NAME = "game_queue";

    public static void main(String[] args) {
        // Receive flat file data from RabbitMQ
        String flatFileData = receiveFlatFile();

        // Convert flat file data back to obstacles objects
        Obstacles obstacle1 = fromFlatFile(flatFileData); // Assuming the flat file contains data for obstacle 1
        Obstacles obstacle2 = fromFlatFile(flatFileData); // Assuming the flat file contains data for obstacle 2
        Obstacles obstacle3 = fromFlatFile(flatFileData); // Assuming the flat file contains data for obstacle 3

        // Print obstacles
        System.out.println("Obstacle 1: " + obstacle1);
        System.out.println("Obstacle 2: " + obstacle2);
        System.out.println("Obstacle 3: " + obstacle3);

        // Existing code...
    }

    private static String receiveFlatFile() {
        String flatFileData = null;
        try {
            // Connect to RabbitMQ
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            // Declare queue
            channel.queueDeclare(RABBITMQ_QUEUE_NAME, false, false, false, null);

            // Receive message
            GetResponse response = channel.basicGet(RABBITMQ_QUEUE_NAME, true);
            if (response != null) {
                byte[] body = response.getBody();
                flatFileData = new String(body, "UTF-8");
            }

            // Close connection
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        return flatFileData;
    }

    private static Obstacles fromFlatFile(String flatFileData) {
        // Implement converting flat file data back to obstacles object
        // This code depends on the specific format of the flat file
        // For now, let's return a placeholder obstacles object
        return new Obstacles("PlaceholderObstacle", 0, 0, 0);
    }
}















