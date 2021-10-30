package br.com.alura.ecommerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaDispatcher {

    private final KafkaProducer<String, String> kafkaProducer;

    public KafkaDispatcher() {

        //create a kafka producer
        kafkaProducer = new KafkaProducer<String, String>(properties());

    }

    public void send(String topic, String key, String value) throws InterruptedException, ExecutionException {

        final var record = new ProducerRecord<String, String>(topic, key, value);

        for (var i =0; i<10; i++) {

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
