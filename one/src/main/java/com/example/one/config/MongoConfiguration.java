package com.example.one.config;

import com.example.one.metrics.MyMongoMetricsCommandListener;
import com.mongodb.Block;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.connection.ConnectionPoolSettings;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.mongodb.MongoMetricsCommandListener;
import io.micrometer.core.instrument.binder.mongodb.MongoMetricsConnectionPoolListener;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;

@Configuration
public class MongoConfiguration {
    /**
     * register
     * @param meterRegistry me
     * @return https://stackoverflow.com/questions/60991985/spring-boot-micrometer-metrics-for-mongodb
     */
    @Bean
    public MongoClientSettingsBuilderCustomizer mongoClientSettingsBuilderCustomizer(MeterRegistry meterRegistry) {
        // return builder -> builder.addCommandListener(new MongoMetricsCommandListener(meterRegistry));
        Block<ConnectionPoolSettings.Builder> z = builder ->
                builder.addConnectionPoolListener(new MongoMetricsConnectionPoolListener(meterRegistry));
        return builder -> builder.applyToConnectionPoolSettings(z).addCommandListener(new MyMongoMetricsCommandListener(meterRegistry));
    }

//
//    @Bean
//    public MongoClientFactoryBean mongoClientFactoryBean(MongoProperties properties, MeterRegistry meterRegistry) {
//        MongoClientFactoryBean mongoClientFactoryBean = new MongoClientFactoryBean();
//        mongoClientFactoryBean.setConnectionString(new ConnectionString(properties.getUri()));
//        MongoClientSettings settings = MongoClientSettings.builder()
//                .addCommandListener(new MongoMetricsCommandListener(meterRegistry))
//                .applyToConnectionPoolSettings(builder ->
//                        builder.addConnectionPoolListener(new MongoMetricsConnectionPoolListener(meterRegistry)))
//                .build();
//        mongoClientFactoryBean.setMongoClientSettings(settings);
//        return mongoClientFactoryBean;
//    }
}
