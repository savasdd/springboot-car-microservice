package com.car.config;

import com.car.event.StockEvent;
import com.car.service.StockKafkaService;
import com.car.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Slf4j
@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaStreamsConfig {

    private final StockKafkaService service;

    public KafkaStreamsConfig(StockKafkaService service) {
        this.service = service;
    }

    @Bean
    public KStream<String, StockEvent> stream(StreamsBuilder builder) {
        JsonSerde<StockEvent> orderSerde = new JsonSerde<>(StockEvent.class);
        KStream<String, StockEvent> stream = builder.stream("stock", Consumed.with(Serdes.String(), orderSerde));

        return stream;
    }

    @Bean
    public KTable<String, StockEvent> table(StreamsBuilder builder) {
        KeyValueBytesStoreSupplier store = Stores.persistentKeyValueStore("stock");
        JsonSerde<StockEvent> orderSerde = new JsonSerde<>(StockEvent.class);
        KStream<String, StockEvent> stream = builder.stream("stock", Consumed.with(Serdes.String(), orderSerde));
        return stream.toTable(Materialized.<String, StockEvent>as(store).withKeySerde(Serdes.String()).withValueSerde(orderSerde));
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setThreadNamePrefix("kafkaSender-");
        executor.initialize();
        return executor;
    }

    @KafkaListener(id = "orders", topics = "stock", groupId = "stock_kafka")
    public void onEvent(String order) {
        log.info("Received: {}", order);
        var event = JsonUtil.fromJson(order, StockEvent.class);
        service.confirmStock(event);
    }


    //TODO
//    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
//    public KafkaStreamsConfiguration kStreamsConfigs() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "stock_kafka");
//        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass().getName());
//        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
//        props.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class.getName());
//        return new KafkaStreamsConfiguration(props);
//    }
//
//    @Bean
//    public StreamsBuilderFactoryBeanConfigurer configurer() {
//        return fb -> fb.setStateListener((newState, oldState) -> {
//            System.out.println("State transition from " + oldState + " to " + newState);
//        });
//    }
//
//    @Bean
//    public KStream<Long, String> kStream(StreamsBuilder kStreamBuilder) {
//        KStream<Long, String> stream = kStreamBuilder.stream("stock");
//        stream
//                .mapValues((ValueMapper<String, String>) String::toUpperCase)
//                .groupByKey()
//                .windowedBy(TimeWindows.of(Duration.ofMillis(1_000)))
//                .reduce((String value1, String value2) -> value1 + value2, Named.as("windowStore"))
//                .toStream()
//                .map((windowedId, value) -> new KeyValue<>(windowedId.key(), value))
//                .filter((i, s) -> s.length() > 40).to("streamingTopic2");
//
//        stream.print(Printed.toSysOut());
//
//        return stream;
//    }

}