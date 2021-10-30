package br.com.alura.ecommerce;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;


/**
 * A simple kafkaProducer that writes message in the topic
 */
public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {


        var userId = UUID.randomUUID().toString();

        //create a message
        final String value = userId + ",54645, 2021";


        try (var kafkaDispatcher = new KafkaDispatcher()) {

            for (var i = 0; i < 10; i++) {
                kafkaDispatcher.send("JAVA-TOPIC", value, value);

                var email = "Thank you for your order, your has been proccessed!";
                kafkaDispatcher.send("ECOMMERCE-EMAIL", email, email);
            }
        }


    }
}
