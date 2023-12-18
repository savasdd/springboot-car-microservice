package com.car.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.streams.StreamsConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import java.util.*

@Configuration
@EnableKafka
@ConditionalOnProperty(value = ["kafka.enabled"], havingValue = "true")
class KafkaConsumerConfig {
    private var bootstrapAddress: String? = null
    private val GROUP_ID: String? = "stock_kafka"


    @Autowired
    fun  KafkaConsumerConfig(env: Environment) {
        bootstrapAddress = env.getProperty("spring.kafka.bootstrap-servers")
    }

    fun stringConsumerFactory(groupId: String?): ConsumerFactory<String?, String?>? {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress!!
        if (groupId != null) {
            props[ConsumerConfig.GROUP_ID_CONFIG] = groupId
        } else {
            props[ConsumerConfig.GROUP_ID_CONFIG] = UUID.randomUUID().toString()
        }
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG] = 300000
        props[ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG] = 1000
        props[ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG] = 900000
        props[ConsumerConfig.MAX_POLL_RECORDS_CONFIG] = 100
        props[ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG] = 600000
        props[StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG] = Serdes.String().javaClass
        props[StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG] = Serdes.String().javaClass
        return DefaultKafkaConsumerFactory(props)
    }

    fun stringKafkaListenerContainerFactory(groupId: String?): ConcurrentKafkaListenerContainerFactory<String, String>? {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = stringConsumerFactory(groupId)!!
        return factory
    }

    @Bean
    fun stringKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String>? {
        return stringKafkaListenerContainerFactory(GROUP_ID)
    }

    fun stringFirstConsumerFactory(groupId: String?): ConsumerFactory<String?, String?>? {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress!!
        if (groupId != null) {
            props[ConsumerConfig.GROUP_ID_CONFIG] = groupId
        } else {
            props[ConsumerConfig.GROUP_ID_CONFIG] = UUID.randomUUID().toString()
        }
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG] = 300000
        props[ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG] = 1000
        props[ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG] = 900000
        props[ConsumerConfig.MAX_POLL_RECORDS_CONFIG] = 100
        props[ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG] = 600000
        props[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = "false"
        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
        props[StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG] = Serdes.String().javaClass
        props[StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG] = Serdes.String().javaClass
        return DefaultKafkaConsumerFactory(props)
    }

    fun stringFistKafkaListenerContainerFactory(groupId: String?): ConcurrentKafkaListenerContainerFactory<String, String>? {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = stringFirstConsumerFactory(groupId)!!
        return factory
    }

    @Bean
    fun stringFirstKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String>? {
        return stringFistKafkaListenerContainerFactory(GROUP_ID)
    }

    @Bean
    fun filterStringKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String>? {
        return stringKafkaListenerContainerFactory(GROUP_ID)
    }

}