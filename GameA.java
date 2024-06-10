import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//GameA class
public class GameA {
    private static final String RABBITMQ_QUEUE_NAME = "game_queue";
    private static final String WEB_SERVICE_URL = "http://localhost:8080/hello";

    public static void main(String[] args) {
        // Create obstacles objects
        Obstacles obstacle1 = new Obstacles("Small Asteroid", 20, 5, 10);
        Obstacles obstacle2 = new Obstacles("Large Asteroid", 40, 10, 5);
        Obstacles obstacle3 = new Obstacles("BlackHole", 30, 10, 20);

        // Print obstacles
        System.out.println("Obstacle 1: " + obstacle1);
        System.out.println("Obstacle 2: " + obstacle2);
        System.out.println("Obstacle 3: " + obstacle3);

        // Send obstacles object as a flat file using RabbitMQ
        sendFlatFile(obstacle1);
        System.out.println("Response sent: Obstacle 1 sent via RabbitMQ.");
        sendFlatFile(obstacle2);
        System.out.println("Response sent: Obstacle 2 sent via RabbitMQ.");
        sendFlatFile(obstacle3);
        System.out.println("Response sent: Obstacle 3 sent via RabbitMQ.");

        // Send obstacles object as JSON using Web Service
        sendJSON(obstacle1);
        System.out.println("Response sent: Obstacle 1 sent via Web Service.");
        sendJSON(obstacle2);
        System.out.println("Response sent: Obstacle 2 sent via Web Service.");
        sendJSON(obstacle3);
        System.out.println("Response sent: Obstacle 3 sent via Web Service.");
    }

    private static void sendFlatFile(Obstacles obstacle) {
        // Convert obstacles object to flat file format
        String flatFileData = obstacle.toString();

        // Write flat file to disk
        try {
            File file = new File("obstacle_data.txt");
            FileWriter writer = new FileWriter(file);
            writer.write(flatFileData);
            writer.close();
            System.out.println("Flat file saved: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Implement sending flat file data via RabbitMQ
        // This code depends on the RabbitMQ implementation
    }

    private static void sendJSON(Obstacles obstacle) {
        // Implement sending obstacles object as JSON using Web Service
        // This code depends on the specific web service implementation
    }
}





