package com.example.one.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.mongodb.MongoMetricsCommandListener;
import io.micrometer.core.instrument.binder.mongodb.MongoMetricsConnectionPoolListener;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import service.MongoClient;

// @Configuration
public class FullMongoConfiguration {

    // @Bean
    // can not work
    public MongoClientFactoryBean mongoClientFactoryBean(MongoProperties properties, MeterRegistry meterRegistry) {
        MongoClientFactoryBean mongoClientFactoryBean = new MongoClientFactoryBean();
        mongoClientFactoryBean.setConnectionString(new ConnectionString("mongodb://admin:123456@127.0.0.1:27017"));
        MongoClientSettings settings = MongoClientSettings.builder()
                .addCommandListener(new MongoMetricsCommandListener(meterRegistry))
                .applyToConnectionPoolSettings(builder ->
                        builder.addConnectionPoolListener(new MongoMetricsConnectionPoolListener(meterRegistry)))
                .build();
        mongoClientFactoryBean.setMongoClientSettings(settings);
        return mongoClientFactoryBean;
    }

}