# Alura kafka course
Alura Kafka Course

#Kafka Commands

##Set up zookeeper

`.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
`

##Set up kafka

`.\bin\windows\kafka-server-start.bat .\config\server.properties
`
##Create a topic

`bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic TestTopic
`

##List topics

`\kafka-topics.bat --list --bootstrap-server localhost:9092
`

##Create producer

`bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic  TestTopic
`
##Create conumser

`bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic  TestTopic --from-beginning
`

##Change partitions number of an existing topic
`bin/windows/kafka-topics.bat --alter --zookeeper localhost:2181 --topic JAVA-TOPIC --partitions 3
`

##Describe consumer groups
`bin\windows\kafka-consumer-groups.bat --all-groups --bootstrap-server localhost:9092 --describe
`
##**Util Samples**
Simple kafka consumer that reads from a topic - aula-01/src/main/java/br/com/alura/ecommerce/FraudDetectorService.java
</br>
Simple kafka producer that writes a message in a topic - aula-01/src/main/java/br/com/alura/ecommerce/NewOrderMain.java
</br>
Simple kafka producer that writes a messages in more than one topic - aula-02/src/main/java/br/com/alura/ecommerce/NewOrderMain.java
</br>
*A consumer that reads messages from more than one top*ic - aula-02/src/main/java/br/com/alura/ecommerce/LogService.java


#QA

#Q - How to solve partition rebalancing problems in consumers?
Set the property MAX_POLL_RECORDS_CONFIG to 1, to commit often
`properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
`