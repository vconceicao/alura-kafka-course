package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.IOException;

/**
 * A simple Kafka Consumer that reads from a topic
 */

public class FraudDetectorService {

    public static void main(String[] args) throws IOException {

        final var fraudDetectorService = new FraudDetectorService();

        try (final KafkaService kafkaService = new KafkaService(FraudDetectorService.class.getSimpleName(), "JAVA-TOPIC", fraudDetectorService::parse)) {

            kafkaService.run();
        }
    }

    private void parse(ConsumerRecord<String, String> r) {
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
    }

}
