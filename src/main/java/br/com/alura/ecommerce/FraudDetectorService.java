package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

/**
*  A simple Kafka Consumer that reads from a topic
* */
public class FraudDetectorService {

    public static void main(String[] args) {


        //creating a kafka consumer
        final var consumer = new KafkaConsumer<String, String>(properties());


        //subscribing to a topic
        consumer.subscribe(Collections.singletonList("JAVA-TOPIC"));

        //Infinite loop to keep checking for new messeges
        while (true) {


            //asking to consumer if theres new register in the topic
            final ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            //if there`s registers proccess them
            if (!records.isEmpty()) {
                System.out.println("Registers found");
                records.forEach(r -> {

                    System.out.println("Proccessing order, checking for  frauds....");
                    System.out.println(r.key());
                    System.out.println(r.value());
                    System.out.println(r.partition());
                    System.out.println(r.offset());

                    //simulate the proccess of sleeping(optional)
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //OK, WE'RE DONE HERE
                    System.out.println("Order processed");

                });
            }

        }

    }

    private static Properties properties() {
        final var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, FraudDetectorService.class.getSimpleName());
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, FraudDetectorService.class.getSimpleName() + UUID.randomUUID().toString());
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");

        return properties;
    }
}
