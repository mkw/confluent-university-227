package partial;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Pattern;

public class ShakespeareConverterConsumerProducer {

  /**
   * Regular expression for parsing the line number and line
   */
  Pattern pattern = Pattern.compile("^\\s*(\\d*)\\s*(.*)$");

  static HashMap<String, Integer> shakespeareWorkToYearWritten = new HashMap<String, Integer>();


  /**
   * Creates a ConsumerConnector that reads a stream, converts to Avro and
   * publishes to a KafkaProducer
   *
   * @throws InterruptedException
   */
  public void createConsumer() throws InterruptedException {

    // Create the list of works to their publication date
    shakespeareWorkToYearWritten.put("Hamlet", 1600);
    shakespeareWorkToYearWritten.put("Julius Caesar", 1599);
    shakespeareWorkToYearWritten.put("Macbeth", 1605);
    shakespeareWorkToYearWritten.put("Merchant of Venice", 1596);
    shakespeareWorkToYearWritten.put("Othello", 1604);
    shakespeareWorkToYearWritten.put("Romeo and Juliet", 1594);


    Properties prodProps = new Properties();
    prodProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093");
    prodProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
    prodProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
    // Configure schema repository server
    prodProps.put("schema.registry.url", "http://localhost:28081");

    try (KafkaProducer<Object, Object> producer = new KafkaProducer<Object, Object>(prodProps)) {

      // Properties for the Consumer

      Properties props = new Properties();
      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093");
      props.put(ConsumerConfig.GROUP_ID_CONFIG, "testgroup");
      props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
      props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
      props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");


      // TODO: Create a KafkaConsumer, and subscribe to shakespeare_topic


/*
            while(true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {

                    // Get original strings from message and convert to Avro
                    ShakespeareKey shakespeareKey = getShakespeareKey(record.key());

                    // TODO: Create the ShakespeareValue

                    // TODO: Create the ProducerRecord with the Avro objects and send them

                }
            }
*/
    }
  }


  public static void main(String[] args) {
    ShakespeareConverterConsumerProducer consumer = new ShakespeareConverterConsumerProducer();

    try {
      consumer.createConsumer();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  /**
   * Creates the ShakespeareKey object with the work of Shakespeare
   *
   * @param key
   *            The name of the work of Shakespeare
   * @return The ShakespeareKey object with the work of Shakespeare
   */
    /*private ShakespeareKey getShakespeareKey(String key) {
        Integer yearWritten = shakespeareWorkToYearWritten.get(key);

        if (yearWritten == null) {
            throw new RuntimeException(
                    "Could not find year written for \"" + key + "\"");
        }

        // TODO: return a ShakespeareKey object containing key and yearWritten
    }*/

  /**
   * Creates the ShakespeareLine object with the line from Shakespeare
   *
   * @param line
   *            The line of Shakespeare to parse
   * @return The ShakespeareLine object with the line from Shakespeare
   */
    /*private ShakespeareValue getShakespeareLine(String line) {
        Matcher matcher = pattern.matcher(line);

        // Use a regex to parse out the line number from the rest of the line
        if (matcher.matches()) {
            // Get the line number and line and create the ShakespeareLine
            int lineNumber = Integer.parseInt(matcher.group(1));
            String lineOfWork = matcher.group(2);

            // TODO: return a ShakespeareValue object with the line number and line of work

        } else {
            // Line didn't match the regex
            System.out.println("Did not match Regex:" + line);

            return null;
        }
    }*/
}
