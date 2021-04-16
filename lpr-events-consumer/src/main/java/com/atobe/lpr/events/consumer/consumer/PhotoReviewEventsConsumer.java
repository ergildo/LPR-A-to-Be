package com.atobe.lpr.events.consumer.consumer;

import com.atobe.lpr.events.consumer.model.Photo;
import com.atobe.lpr.events.consumer.service.PhotoReviewEventsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PhotoReviewEventsConsumer {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PhotoReviewEventsService photoReviewEventsService;

    @KafkaListener(topics = {"photos-review-events"})
    public void onMessage(ConsumerRecord<Integer,String> consumerRecord){

        log.info("ConsumerRecord : {} ", consumerRecord );

        String value = consumerRecord.value();

        log.info("Value : {} ", value );

        try {
            Photo photo = objectMapper.readValue(value, Photo.class);
            photoReviewEventsService.process(photo);
        } catch (JsonProcessingException e) {
           throw new RuntimeException(e);
        }

    }
}
