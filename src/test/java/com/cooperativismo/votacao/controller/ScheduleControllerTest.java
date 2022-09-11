package com.cooperativismo.votacao.controller;

import com.cooperativismo.votacao.dto.request.ScheduleRequestDto;
import com.cooperativismo.votacao.model.Schedule;
import com.cooperativismo.votacao.service.ScheduleService;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ScheduleControllerTest {

    @Mock
    private ScheduleService scheduleService;

    @InjectMocks
    private ScheduleController scheduleController;

    @Test
    public void returnListScheduleTest(){
        List<Schedule> scheduleList = new ArrayList<>();
        Schedule schedule = buildSchedule();
        scheduleList.add(schedule);

        Mockito.when(scheduleService.findAll()).thenReturn(scheduleList);

        ResponseEntity<?> response = scheduleController.findAll();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(1, ((ArrayList<?>) response.getBody()).size());
    }

    @Test
    public void createScheduleTest() throws URISyntaxException {
        val requestDto = buildScheduleDto();
        val schedule = buildSchedule();

        Mockito.when(scheduleService.registerSchedule(requestDto)).thenReturn(schedule);

        ResponseEntity<?> response = scheduleController.saveSchedule(requestDto);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void findScheduleById(){

        val requestDto = buildScheduleDto();
        val schedule = buildSchedule();

        Mockito.when(scheduleService.findById(requestDto.getId())).thenReturn(schedule);

        ResponseEntity<?> response = scheduleController.findById(requestDto.getId());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
    }


    private ScheduleRequestDto buildScheduleDto(){
        val schedule = new ScheduleRequestDto();
        schedule.setId("SCHEDULE1");
        schedule.setName("TESTE-SCHEDULE");

        return schedule;
    }

    private Schedule buildSchedule(){
        val schedule = new Schedule();
        schedule.setId(buildScheduleDto().getId());
        schedule.setName(buildScheduleDto().getName());
        schedule.setCodeSchedule("TEST1");

        return schedule;
    }
}