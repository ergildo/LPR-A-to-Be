package com.atobe.producer;

import com.atobe.exception.PublishEventException;
import com.atobe.model.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class EventProducer {

  @Autowired
  KafkaTemplate<Integer, String> kafkaTemplate;

  ObjectMapper objectMapper = new ObjectMapper();


  public void send(Event event) {

    try {
      Integer key = event.getKey();
      String value = objectMapper.writeValueAsString(event.getBody());

      ProducerRecord<Integer, String> producerRecord = buildProducerRecord(key, value, event.getTopic());

      ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.send(producerRecord);

      listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
        @Override
        public void onFailure(Throwable ex) {
          handleFailure(key, value, ex);
        }

        @Override
        public void onSuccess(SendResult<Integer, String> result) {
          handleSuccess(key, value, result);
        }
      });
    } catch (Exception e) {
      throw new PublishEventException(e);
    }


  }

  private ProducerRecord<Integer, String> buildProducerRecord(Integer key, String value, String topic) {


    List<Header> recordHeaders = List.of(new RecordHeader("event-source", "lpr".getBytes()));

    return new ProducerRecord<>(topic, null, key, value, recordHeaders);
  }


  private void handleFailure(Integer key, String value, Throwable ex) {
    log.error("Error Sending the Message and the exception is {}", ex.getMessage());
    try {
      throw ex;
    } catch (Throwable throwable) {
      log.error("Error in OnFailure: {}", throwable.getMessage());
    }


  }

  private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
    log.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}", key, value, result.getRecordMetadata().partition());
  }


}
