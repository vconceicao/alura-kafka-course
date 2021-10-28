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

        //create a kafka producer
        final var kafkaProducer = new KafkaProducer<String, String>(properties());

        for (var i =0; i<100; i++) {
            //creating a new id for the user, this is because kafka will use this info as key to parallelize the messages consumed
            var userId = UUID.randomUUID().toString();

            //create a message
            final String value =  userId + ",54645, 2021";
            String email = "Thank you for your order, your has been proccessed!";

            //create a producer record within a topic, or simply writing a message in a topic
            final ProducerRecord<String, String> record = new ProducerRecord<>("JAVA-TOPIC", value, value);

            //create a producer record within a topic, or simply writing a message in a topic
            final ProducerRecord<String, String> emailRecord = new ProducerRecord<>("ECOMMERCE-EMAIL", email, email);

            //send a message to the topic
            final Callback callback = (data, exception) -> {

                if (Objects.nonNull(exception)) {
                    exception.printStackTrace();
                    return;
                }

                //the message sent
                System.out.println("mensagem enviada com sucesso para o topico " + data.topic() + " offset " + data.offset() + " na particao " + data.partition() + " timestamp " + data.timestamp());

            };

            kafkaProducer.send(record, callback).get();//get will return a result in the future
            kafkaProducer.send(emailRecord, callback).get();//get will return a result in the future
        }
    }

    private static Properties properties() {
        final var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


        return properties;
    }
}
