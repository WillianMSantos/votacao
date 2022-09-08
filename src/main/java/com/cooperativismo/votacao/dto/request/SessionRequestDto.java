package com.cooperativismo.votacao.dto.request;

import com.cooperativismo.votacao.model.Schedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionRequestDto {

    private String id;
    private Long expiryMinutes;
}
