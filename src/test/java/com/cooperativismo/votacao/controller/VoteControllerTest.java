package com.cooperativismo.votacao.controller;


import com.cooperativismo.votacao.model.Schedule;
import com.cooperativismo.votacao.model.Vote;
import com.cooperativismo.votacao.service.VoteService;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class VoteControllerTest {

    @Mock
    public VoteService voteService;

    @InjectMocks
    public VoteController voteController;


    @Test
    public void shouldReturnZeroVote() {

        ResponseEntity<?> response = voteController.all();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(0, ((LinkedList<?>) response.getBody()).size());
    }


    @Test
    public void findByIdTest() {

        val vote = buildVote();

        ResponseEntity<?> response = voteController.findById(vote.getId());

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }


    private Vote buildVote(){

        val vote = new Vote();
        vote.setId("TESTE1");
        vote.setCpf("123456789");
        vote.setSchedule(buildSchedule());
        vote.setVote(true);

        return vote;
    }

    private Schedule buildSchedule(){
        val schedule = new Schedule();
        schedule.setId("SCHEDULE1");
        schedule.setName("TESTE-SCHEDULE");
        schedule.setCodeSchedule("TEST1");

        return schedule;
    }
}