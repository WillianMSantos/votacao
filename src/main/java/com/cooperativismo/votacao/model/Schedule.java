package com.cooperativismo.votacao.model;

import com.cooperativismo.votacao.model.enums.ResultSchedule;
import com.cooperativismo.votacao.model.enums.StatusSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Schedule {

    @Id
    private String id;
    private String codeSchedule;
    private String name;
}
