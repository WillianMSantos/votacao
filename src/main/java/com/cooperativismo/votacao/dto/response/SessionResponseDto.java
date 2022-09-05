package com.cooperativismo.votacao.dto.response;

import com.cooperativismo.votacao.model.Session;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionResponseDto {

    private String sessionCode;
    private LocalDateTime startDate;
    private Long expiryMinutes;


    public static SessionResponseDto toSessionResponseDto(Session session) {

        return SessionResponseDto.builder()
                .sessionCode(session.getSessionCode())
                .startDate(session.getStartDate())
                .expiryMinutes(session.getExpiryMinutes())
                .build();
    }

}
