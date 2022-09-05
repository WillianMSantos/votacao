package com.cooperativismo.votacao.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session {

    @Id
    private String id;
    private String sessionCode;
    private LocalDateTime startDate;
    private Long expiryMinutes;
    private Schedule schedule;

    public Session schedule(Schedule schedule) {
        this.schedule = schedule;
        return this;
    }

}
