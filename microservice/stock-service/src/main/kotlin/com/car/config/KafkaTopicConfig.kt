package com.car.config

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin


@Configuration
@ConditionalOnProperty(value = ["kafka.enabled"], havingValue = "true")
class KafkaTopicConfig {

    private var bootstrapAddress: String? = null

    fun KafkaTopicConfig(env: Environment) {
        bootstrapAddress = env.getProperty("spring.kafka.bootstrap-servers")
    }

    @Bean
    fun kafkaAdmin(): KafkaAdmin? {
        val configs: MutableMap<String, Any> = HashMap()
        configs[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress!!
        return KafkaAdmin(configs)
    }

    @Bean
    fun orders(): NewTopic? {
        return TopicBuilder.name("stock")
            .partitions(3)
            .compact()
            .build()
    }

    @Bean
    fun paymentTopic(): NewTopic? {
        return TopicBuilder.name("order")
            .partitions(3)
            .compact()
            .build()
    }
}