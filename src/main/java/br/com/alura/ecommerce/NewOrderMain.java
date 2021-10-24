package br.com.alura.ecommerce;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ExecutionException;


/**
* A simple kafkaProducer that writes message in the topic
*/
public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //create a kafka producer
        final var kafkaProducer = new KafkaProducer<String, String>(properties());

        //create a message
        final String value = "5464545, 54645, 2021";

        //create a producer record within a topic, or simply writing a message in a topic
        final ProducerRecord<String, String> record = new ProducerRecord<>("JAVA-TOPIC", value, value);

        //send a message to the topic
        kafkaProducer.send(record, (data, exception) -> {

            if (Objects.nonNull(exception)) {
                exception.printStackTrace();
                return;
            }

            //the message sent
            System.out.println("mensagem enviada com sucesso para o topico " + data.topic() + " offset " + data.offset() + " na particao " + data.partition() + " timestamp " + data.timestamp());

        }).get();//get will return a result in the future
    }

    private static Properties properties() {
        final var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


        return properties;
    }
}
