package com.cooperativismo.votacao.service;

import com.cooperativismo.votacao.dto.request.SessionRequestDto;
import com.cooperativismo.votacao.dto.response.SessionResponseDto;
import com.cooperativismo.votacao.exception.ExistingSessionException;
import com.cooperativismo.votacao.exception.ScheduleNotFoundException;
import com.cooperativismo.votacao.model.Schedule;
import com.cooperativismo.votacao.model.Session;
import com.cooperativismo.votacao.repository.ScheduleRepository;
import com.cooperativismo.votacao.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionService {

    private SessionRepository sessionRepository;

    private ScheduleRepository scheduleRepository;

    private ScheduleService scheduleService;

    @Autowired
    public SessionService(SessionRepository sessionRepository, ScheduleRepository scheduleRepository,
                          ScheduleService scheduleService){

        this.sessionRepository = sessionRepository;
        this.scheduleRepository = scheduleRepository;
        this.scheduleService = scheduleService;
    }


    public SessionResponseDto createSession(String id, SessionRequestDto sessionRequestDto) {
        Session session = new Session();
        Schedule schedule = scheduleService.findById(id);

        session.setId(sessionRequestDto.getId());
        session.setSchedule(schedule);

        if (sessionRepository.findById(sessionRequestDto.getId()).isPresent()){
            throw new ExistingSessionException();
        }

        if (schedule.equals(null)){
            throw new ScheduleNotFoundException();
        }

        if(session.getStartDate() == null) {
            session.setStartDate(LocalDateTime.now());
        }

        if(sessionRequestDto.getExpiryMinutes() == null) {
            session.setExpiryMinutes(1L);
        }else {
            session.setExpiryMinutes(sessionRequestDto.getExpiryMinutes());
        }

        session.setId(sessionRequestDto.getId());
        sessionRepository.save(session);

        return SessionResponseDto.toSessionResponseDto(session);
    }

    public List<SessionResponseDto> findAll() {
        List<Session> allSessions = sessionRepository.findAll();

        return allSessions.stream()
                          .map(SessionResponseDto::toSessionResponseDto)
                          .collect(Collectors.toList());
    }

    public void deleteSession(String id) {
        sessionRepository.findById(id)
                         .map(session -> {
                             sessionRepository.delete(session);
                             return Void.TYPE; }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                                                               "Session not found"));
    }


    public SessionResponseDto findSessionBySessionCode(String id) {
        Session session = findById(id);
        return SessionResponseDto.toSessionResponseDto(session);
    }


    public Session findById(String id) {
        return sessionRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Session not found"));

    }

    public Session findByIdAndScheduleId(String idSession, String scheduleId) {

        return sessionRepository.findByIdAndScheduleId(idSession, scheduleId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Session not found"));
    }

}
