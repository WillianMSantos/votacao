package com.cooperativismo.votacao.dto.request;

import lombok.Data;
import org.apache.kafka.common.protocol.types.Field;

@Data
public class ScheduleRequestDto {

    private String id;
    private String name;
}
