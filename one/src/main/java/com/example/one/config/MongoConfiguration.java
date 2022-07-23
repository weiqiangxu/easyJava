package com.example.one.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.mongodb.MongoMetricsCommandListener;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfiguration {
    /**
     * register
     * @param meterRegistry me
     * @return https://stackoverflow.com/questions/60991985/spring-boot-micrometer-metrics-for-mongodb
     */
    @Bean
    public MongoClientSettingsBuilderCustomizer mongoClientSettingsBuilderCustomizer(MeterRegistry meterRegistry) {
        return builder -> builder.addCommandListener(new MongoMetricsCommandListener(meterRegistry));
    }
}
