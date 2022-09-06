package com.cooperativismo.votacao.controller;

import com.cooperativismo.votacao.dto.request.ScheduleRequestDto;
import com.cooperativismo.votacao.model.Schedule;
import com.cooperativismo.votacao.service.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "Schedules")
@RequestMapping("api/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @ApiOperation(value = "Create schedules")
    @PostMapping("/register-schedule")
    @ResponseStatus(HttpStatus.CREATED)
    private String saveSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {

        Schedule schedule = scheduleService.registerSchedule(scheduleRequestDto);
        return schedule.getCodeSchedule();
    }

    @ApiOperation(value = "List schedules")
    @GetMapping("/list-schedules")
    @ResponseStatus(HttpStatus.OK)
    public List<Schedule> findAll(){return scheduleService.findAll(); }


    @ApiOperation(value = "Delete schedule")
    @DeleteMapping("/delete-schedule")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id) {scheduleService.deleteSchedule(id);}


    @ApiOperation(value = "Find schedule")
    @GetMapping("/schedule/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void findById(@PathVariable String id) {
        scheduleService.findById(id);
    }
}
