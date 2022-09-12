package com.cooperativismo.votacao.service;

import com.cooperativismo.votacao.dto.request.ScheduleRequestDto;
import com.cooperativismo.votacao.exception.ScheduleNotFoundException;
import com.cooperativismo.votacao.model.Schedule;
import com.cooperativismo.votacao.repository.ScheduleRepository;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleService scheduleService;



    @Test
    public void createScheduleTest() {
        val requestDto = buildScheduleDto();
        Schedule schedule;

        schedule = scheduleService.registerSchedule(requestDto);


        assertEquals(requestDto.getId(), schedule.getId());
    }

    @Test(expected = ScheduleNotFoundException.class)
    public void deleteTest(){
        val schedule = buildSchedule();

        scheduleService.deleteSchedule(schedule.getId());
    }


    @Test
    public void listSchedules() {
        List<Schedule> scheduleList = new ArrayList<>();
        val schedule = buildSchedule();
        scheduleList.add(schedule);

        Mockito.when(scheduleRepository.findAll()).thenReturn(scheduleList);

        List<Schedule> scheduleList2 = scheduleService.findAll();
        assertEquals(1, scheduleList2.size());
    }

    @Test(expected = ScheduleNotFoundException.class)
    public void findByIdNotFoundTest() {
        val schedule = buildSchedule();
        String id = "test2";


        scheduleService.findById(schedule.getId());
    }



    @Test
    public void findByIdTest() {
        val schedule = buildSchedule();

        Mockito.when(scheduleRepository.findById(schedule.getId())).thenReturn(Optional.of(schedule));

        Schedule schedule1 = scheduleService.findById(schedule.getId());
        assertEquals(schedule.getId(), schedule1.getId());
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