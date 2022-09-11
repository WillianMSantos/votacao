package com.cooperativismo.votacao.service;

import com.cooperativismo.votacao.dto.request.ScheduleRequestDto;
import com.cooperativismo.votacao.model.Schedule;
import com.cooperativismo.votacao.repository.ScheduleRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ScheduleService {

    private ScheduleRepository scheduleRepository;


    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository){
        this.scheduleRepository = scheduleRepository;
    }


    public Schedule registerSchedule(ScheduleRequestDto scheduleRequestDto) {

        val schedule = new Schedule();

        schedule.setId(scheduleRequestDto.getId());
        schedule.setName(scheduleRequestDto.getName());
        scheduleRepository.save(schedule);
        return schedule;
    }

    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }


    public void deleteSchedule(String id) {
        scheduleRepository.findById(id)
                          .map(schedule -> {
                              scheduleRepository.delete(schedule);
                              return Void.TYPE;}).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                              "Schedule not found"));
    }

    public Schedule findById(String id) {

        return scheduleRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Schedule not found"));
    }

}
