package br.com.alura.ecommerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Objects;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;


/**
* A simple kafkaProducer that writes message in the topic
*/
public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {


        var userId = UUID.randomUUID().toString();

        //create a message
        final String value = userId + ",54645, 2021";

        final var kafkaDispatcher = new KafkaDispatcher();
        kafkaDispatcher.send("JAVA-TOPIC", value, value);

        var email = "Thank you for your order, your has been proccessed!";
        kafkaDispatcher.send("ECOMMERCE-EMAIL", email, email);


    }
}
