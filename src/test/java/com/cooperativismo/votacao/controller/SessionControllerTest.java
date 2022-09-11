package com.cooperativismo.votacao.controller;

import com.cooperativismo.votacao.dto.request.SessionRequestDto;
import com.cooperativismo.votacao.dto.response.SessionResponseDto;
import com.cooperativismo.votacao.model.Schedule;
import com.cooperativismo.votacao.service.SessionService;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SessionControllerTest {

    @Mock
    private SessionService sessionService;

    @InjectMocks
    public SessionController sessionController;

    @Test
    public void returnListSessions() {

        List<SessionResponseDto> listDto = new ArrayList<>();
        SessionResponseDto sessionResponseDto = new SessionResponseDto();

        listDto.add(sessionResponseDto);

        Mockito.when(sessionService.findAll()).thenReturn(listDto);

        ResponseEntity<?> response = sessionController.listAll();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(1, ((ArrayList<?>) response.getBody()).size());
    }

    @Test
    public void createSessionTest() throws URISyntaxException {
        val requestDto = buildSessionRequest();
        val responseDto = sessionResponseDto();
        val schedule = buildSchedule();

        Mockito.when(sessionService.createSession(schedule.getId(), requestDto)).thenReturn(responseDto);

        ResponseEntity<?> response = sessionController.createSession(schedule.getId(), requestDto);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }


    @Test
    public void findSessionTest() {

        val responseDto = sessionResponseDto();

        ResponseEntity<?> response = sessionController.findSession(responseDto.getId());

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    private Schedule buildSchedule(){
        val schedule = new Schedule();
        schedule.setId("SCHEDULE1");
        schedule.setName("TESTE-SCHEDULE");
        schedule.setCodeSchedule("TEST1");

        return schedule;
    }

    private SessionRequestDto buildSessionRequest(){

        val session = new SessionRequestDto();
        session.setId("TEST234");
        session.setExpiryMinutes(3L);

        return session;
    }

    private SessionResponseDto sessionResponseDto() {

        val session = new SessionResponseDto();
        session.setId(buildSessionRequest().getId());
        session.setStartDate(LocalDateTime.now());
        session.setExpiryMinutes(buildSessionRequest().getExpiryMinutes());
        session.setSchedule(buildSchedule());

        return session;
    }

}