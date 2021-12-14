package be.tobania.demo.kafka.stockservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.FailOnInvalidTimestamp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;

import java.util.HashMap;
import java.util.Map;


@EnableKafka
@PropertySource("classpath:application.properties")
@Configuration
public class KafkaStreamsConfig {


    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapAddress;

    public void setDefaults(Map<String, Object> config) {
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapAddress);
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "stock-service");
        config.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, FailOnInvalidTimestamp.class);

    }

    @Bean("productStreamBuilder")
    public StreamsBuilderFactoryBean app1StreamBuilderFactoryBean() {
        Map<String, Object> config = new HashMap<>();
        setDefaults(config);
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "stock-service");
        config.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 30);
        config.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, 1);
        config.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, 1);
        return new StreamsBuilderFactoryBean(new KafkaStreamsConfiguration(config));
    }


  /*  @Bean("stockStreamService")
    public KStream<String, String> startProcessing(@Qualifier("productStreamBuilder") StreamsBuilder builder) {
        return builder.stream("shipping", Consumed
                .with(Serdes.String(), Serdes.String()));

    }*/

}