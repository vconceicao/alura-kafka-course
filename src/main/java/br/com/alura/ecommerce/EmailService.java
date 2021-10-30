package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.IOException;


public class EmailService {

    public static void main(String[] args) throws IOException {

        final var emailService = new EmailService();

        try (final KafkaService kafkaService = new KafkaService(FraudDetectorService.class.getSimpleName(), "ECOMMERCE-EMAIL", emailService::parse)){

            kafkaService.run();
        }

    }

    private  void parse(ConsumerRecord<String, String> r) {
        System.out.println("Sending email....");
        System.out.println(r.key());
        System.out.println(r.value());
        System.out.println(r.partition());
        System.out.println(r.offset());

        //simulate the proccess of sleeping(optional)
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //OK, WE'RE DONE HERE
        System.out.println("Email sent");
    }


}
