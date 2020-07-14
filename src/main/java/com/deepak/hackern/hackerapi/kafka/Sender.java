package com.deepak.hackern.hackerapi.kafka;

import com.deepak.hackern.hackerapi.model.KafkaProducerMessage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
public class Sender {

  @Value("${kafka.default-topic}")
  private String jsonTopic;

  @Autowired
  private KafkaTemplate<String, KafkaProducerMessage> kafkaTemplate;

  public void send(KafkaProducerMessage kafkaProducerMessage) {
    log.debug("publishing message to kafka");
    kafkaTemplate.send(jsonTopic, kafkaProducerMessage);
  }
}
