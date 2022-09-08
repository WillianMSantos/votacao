package com.cooperativismo.votacao.kafka;

import com.cooperativismo.votacao.dto.response.VoteResultResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class KafkaReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaReceiver.class);

    public void listen(VoteResultResponseDto voteResultResponseDto) {
        LOGGER.info("message received by the consumer: '{}'", voteResultResponseDto);
    }
}
