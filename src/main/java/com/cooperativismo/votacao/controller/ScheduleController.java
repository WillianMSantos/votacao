package com.cooperativismo.votacao.controller;

import com.cooperativismo.votacao.dto.request.ScheduleRequestDto;
import com.cooperativismo.votacao.model.Schedule;
import com.cooperativismo.votacao.service.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@Api(value = "Schedules")
@RequestMapping("v1/api/schedule")
public class ScheduleController {

    private ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @ApiOperation(value = "Create schedules", response = Schedule.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Schedule successfully created")
    })
    @PostMapping("/register-schedule")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) throws URISyntaxException {

        Schedule schedule = this.scheduleService.registerSchedule(scheduleRequestDto);
        return ResponseEntity.created(new URI(schedule.getId())).body(schedule);
    }

    @ApiOperation(value="List Schedules", response = Schedule.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Schedules found.")
    })
    @GetMapping("/list-schedules")
    public ResponseEntity<?> findAll(){return ResponseEntity.ok(this.scheduleService.findAll()); }


    @ApiOperation(value = "Delete schedule")
    @DeleteMapping("/delete-schedule")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id) {scheduleService.deleteSchedule(id);}


    @ApiOperation(value="Find Schedule", response = Schedule.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Schedule found.")
    })
    @GetMapping("/schedule/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findById(@PathVariable String id) {
        return ResponseEntity.ok(this.scheduleService.findById(id));
    }
}
