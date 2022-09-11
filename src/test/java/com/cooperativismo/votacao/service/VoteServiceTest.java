package com.cooperativismo.votacao.service;

import com.cooperativismo.votacao.dto.request.VoteRequestDto;
import com.cooperativismo.votacao.dto.response.CpfValidationDto;
import com.cooperativismo.votacao.dto.response.VoteResultResponseDto;
import com.cooperativismo.votacao.exception.CpfInvalidException;
import com.cooperativismo.votacao.exception.SessionTimeOutException;
import com.cooperativismo.votacao.model.Schedule;
import com.cooperativismo.votacao.model.Session;
import com.cooperativismo.votacao.model.Vote;
import com.cooperativismo.votacao.repository.VoteRepository;
import lombok.val;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class VoteServiceTest {

    private VoteService voteService;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private  VotingService votingService;

    @Mock
    private ScheduleService scheduleService;

    @Mock
    private SessionService sessionService;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        voteService = new VoteService(restTemplate, voteRepository, sessionService, votingService, scheduleService);
    }


    @Test(expected = CpfInvalidException.class)
    public void cpfAbleForVoteTest() {

        val vote = new Vote();
        vote.setCpf("1234");

        CpfValidationDto cpf = new CpfValidationDto();
        cpf.setStatus("TESTE");

        ResponseEntity<CpfValidationDto> response = new ResponseEntity<>(cpf, HttpStatus.NOT_FOUND);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(CpfValidationDto.class)))
                .thenReturn(response);

        voteService.cpfAbleForVote(vote);
    }

    @Test(expected = SessionTimeOutException.class)
    public void verifyVoteTest() {

        val session = new Session();
        session.setStartDate(LocalDateTime.now());
        session.setExpiryMinutes(-1L);

        val vote = new Vote();
        val schedule = new Schedule();
        schedule.setId("sdfads2f35651as56f4ca");
        vote.setSchedule(schedule);

        when(votingService.buildVoteSchedule(anyString())).thenReturn(VoteResultResponseDto.builder().build());

        voteService.verifyVote(session, vote);
    }

    @Test
    public void checkIfVoteAlreadyExists() {
        val vote = new Vote();
        vote.setCpf("1234");

        val schedule = new Schedule();
        schedule.setId("sdfads2f35651as56f4ca");
        vote.setSchedule(schedule );
        when(voteRepository.findByCpfAndScheduleId(anyString(), anyString())).thenReturn(Optional.of(new Vote()));
        voteService.checkIfVoteAlreadyExists(vote);

    }

    @Test
    public void createVoteTest() {

        val session = new Session();
        session.setStartDate(LocalDateTime.now());
        session.setExpiryMinutes(1L);

        val vote = new Vote();
        val schedule = new Schedule();
        schedule.setId("sdfads2f35651as56f4ca");
        vote.setSchedule(schedule);

        val voteRequestDto = buildVoteRequestDto();

    }


    public VoteRequestDto buildVoteRequestDto(){
        val voteRequestDto = new VoteRequestDto();

        voteRequestDto.setId("asdfefdvfac");
        voteRequestDto.setCpf("123456");
        voteRequestDto.setVote(true);

        return voteRequestDto;
    }

}