package com.cooperativismo.votacao.dto.response;

import com.cooperativismo.votacao.model.Schedule;
import com.cooperativismo.votacao.model.Session;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionResponseDto {

    private String id;
    private LocalDateTime startDate;
    private Long expiryMinutes;
    private Schedule schedule;


    public static SessionResponseDto toSessionResponseDto(Session session) {

        return SessionResponseDto.builder()
                .id(session.getId())
                .startDate(session.getStartDate())
                .expiryMinutes(session.getExpiryMinutes())
                .schedule(session.getSchedule())
                .build();
    }

}
