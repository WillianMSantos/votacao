package com.cooperativismo.votacao.dto.response;

import com.cooperativismo.votacao.model.Schedule;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VoteResultResponseDto {

    private Schedule schedule;
    private Integer totalYes;
    private Integer totalVotes;
    private Integer totalNo;
    private Integer totalSessions;
}
