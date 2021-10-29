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
 * @author vlconceicao
 * Encasulates the logic of the KafkaConsumer
 */

public class KafkaService {
    private final ConsumerFunction parser;
    private final KafkaConsumer<String, String> consumer;

    public KafkaService(String topic, ConsumerFunction parser) {
        //TODO CRIAR UM NOVO PARAMETRO GROUPID
        this.parser = parser;
        this.consumer =  new KafkaConsumer<String, String>(properties());
        //subscribing to a topic
        consumer.subscribe(Collections.singletonList(topic));

        //Infinite loop to keep checking for new messeges

    }

    private static Properties properties() {
        final var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //TODO RECEBER O VALOR DA CHAVE POR PARAMETRO
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, EmailService.class.getSimpleName());
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());


        return properties;
    }

    public void run() {
        while (true) {


            //asking to consumer if theres new register in the topic
            final ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            //if there`s registers proccess them
            if (!records.isEmpty()) {
                System.out.println("Registers found");
                records.forEach(r -> {

                    this.parser.consume(r);

                });
            }

        }

    }
}
