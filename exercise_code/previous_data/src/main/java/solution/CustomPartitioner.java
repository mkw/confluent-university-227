package solution;

import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.Cluster;

public class CustomPartitioner {
    public static class MyPartitioner implements Partitioner {

        @Override
        public void configure(Map<String, ?> configs) {
        }

        @Override
        public void close() {
        }

        @Override
        public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes,
                Cluster cluster) {
            int num = Integer.valueOf(value.toString());
            if (num > 0 && num <= 10) {
                return 0;
            } else if (num > 10 && num <= 20) {
                return 1;
            } else {
                throw new IllegalArgumentException("Unexpected value - should be between 0 and 20" + num);
            }
        }

    }

    public void createProducer() {
        long numberOfEvents = 50;
        Random rand = new Random();

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "solution.CustomPartitioner$MyPartitioner");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < numberOfEvents; i++) {
            producer.send(new ProducerRecord<String, String>("two-p-topic", String.valueOf(rand.nextInt(19) + 1)));
        }

        producer.close();

    }

    public static void main(String[] args) {
        CustomPartitioner customPartitioner = new CustomPartitioner();
        customPartitioner.createProducer();
    }
}
