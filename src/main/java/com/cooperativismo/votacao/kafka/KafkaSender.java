package com.cooperativismo.votacao.kafka;

import com.cooperativismo.votacao.dto.response.VoteResultResponseDto;
import org.apache.kafka.common.protocol.types.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;



@Component
public class KafkaSender {

    private final KafkaTemplate<String, VoteResultResponseDto> kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(KafkaSender.class);

    @Value("${topic.name}")
    private String topic;

    public KafkaSender(KafkaTemplate<String, VoteResultResponseDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(VoteResultResponseDto voteResultResponseDto) {
        kafkaTemplate.send(topic, voteResultResponseDto).addCallback(
                success -> logger.info("message send"),
                failure -> logger.info("message failure")
        );
    }
}
