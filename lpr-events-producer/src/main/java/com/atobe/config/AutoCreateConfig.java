package com.atobe.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Profile("dev")
public class AutoCreateConfig {
    @Value("${events.topics.events-photo-review}")
    String photoReviewTopic;

    @Bean
    public NewTopic libraryEvents(){
        return TopicBuilder.name(photoReviewTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

}
