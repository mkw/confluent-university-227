package partial;

import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.Properties;

public class ShakespeareAvroConsumer {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "newgroup");

        // TODO: Set the key and value deserializers

        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put("schema.registry.url", "http://localhost:28081");
        props.put("specific.avro.reader", "true");

/*
        try (KafkaConsumer<ShakespeareKey, ShakespeareValue> consumer = new KafkaConsumer<>(props)) {

            // TODO: Subscribe to shakespeare_avro_topic


            while (true) {
                ConsumerRecords<ShakespeareKey, ShakespeareValue> records = consumer.poll(100);
                for (ConsumerRecord<ShakespeareKey, ShakespeareValue> record : records) {

                    // TODO: Extract the key and value into ShakespeareKey and ShakespeareValue objects

                    // Output the information with the SpecificRecords

                    // TODO: Write out the play name, year, linenumber, and line
                }
            }
        }
*/
    }
}
