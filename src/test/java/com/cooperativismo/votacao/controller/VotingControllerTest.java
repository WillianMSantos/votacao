package com.cooperativismo.votacao.controller;

import com.cooperativismo.votacao.dto.response.VoteResultResponseDto;
import com.cooperativismo.votacao.model.Schedule;
import com.cooperativismo.votacao.service.VotingService;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class VotingControllerTest {

    @Mock
    private VotingService votingService;

    @InjectMocks
    private VotingController votingController;


    @Test
    public void resultVotingTest() {
        val schedule = buildSchedule();

        ResponseEntity<?> response = votingController.findVotesByScheduleId(schedule.getId());

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    private Schedule buildSchedule(){
        val schedule = new Schedule();
        schedule.setId("SCHEDULE1");
        schedule.setName("TESTE-SCHEDULE");
        schedule.setCodeSchedule("TEST1");

        return schedule;
    }

    public VoteResultResponseDto buildVoteResult(){

        return VoteResultResponseDto.builder()
                .totalNo(4)
                .totalYes(2)
                .totalVotes(6)
                .schedule(buildSchedule())
                .totalSessions(1)
                .build();
    }


}