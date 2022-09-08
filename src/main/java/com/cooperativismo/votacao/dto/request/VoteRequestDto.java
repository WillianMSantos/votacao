package com.cooperativismo.votacao.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteRequestDto {

    private String id;
    private String cpf;
    private Boolean vote;
}
