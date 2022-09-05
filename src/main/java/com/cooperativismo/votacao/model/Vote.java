package com.cooperativismo.votacao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Vote {

    @Id
    private String id;
    private String cpf;
    private Boolean vote;
    private Schedule schedule;

    @JsonIgnore
    public boolean isNew() {
        return getId() == null;
    }

    @JsonIgnore
    public boolean alreadyExist() {
        return getId() != null;
    }
}
