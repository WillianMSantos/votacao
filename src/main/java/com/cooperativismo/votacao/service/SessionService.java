package com.cooperativismo.votacao.service;

import com.cooperativismo.votacao.dto.request.SessionRequestDto;
import com.cooperativismo.votacao.dto.response.SessionResponseDto;
import com.cooperativismo.votacao.exception.ExistingSessionException;
import com.cooperativismo.votacao.exception.ScheduleNotFoundException;
import com.cooperativismo.votacao.model.Schedule;
import com.cooperativismo.votacao.model.Session;
import com.cooperativismo.votacao.repository.IdGenerator;
import com.cooperativismo.votacao.repository.ScheduleRepository;
import com.cooperativismo.votacao.repository.SessionRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    private IdGenerator idGenerator;

    public void createSession(String codeSchedule, SessionRequestDto sessionRequestDto) {
        Session session = new Session();
        Optional<Schedule> schedule = scheduleRepository.findByCodeSchedule(codeSchedule);

        session.setId(idGenerator.generateId());

        if (sessionRepository.findBySessionCode(sessionRequestDto.getSessionCode()).isPresent()){
            throw new ExistingSessionException();
        }

        if (!schedule.isPresent()){
            throw new ScheduleNotFoundException();
        }

        session.setSessionCode(sessionRequestDto.getSessionCode());
        session.setSchedule(schedule.get());
        session = validateTime(sessionRequestDto);
        sessionRepository.save(session);
    }

    private Session validateTime(SessionRequestDto sessionRequestDto) {

        val session = new Session();
        if(sessionRequestDto.getStartDate() == null) {
            session.setStartDate(LocalDateTime.now());
        }

        if(sessionRequestDto.getExpiryMinutes() == null) {
            session.setExpiryMinutes(1L);
        }

        return sessionRepository.save(session);
    }

    public List<SessionResponseDto> findAll() {
        List<Session> allSessions = sessionRepository.findAll();

        return allSessions.stream()
                          .map(SessionResponseDto::toSessionResponseDto)
                          .collect(Collectors.toList());
    }

    public void deleteSession(String sessionCode) {
        sessionRepository.findBySessionCode(sessionCode)
                         .map(session -> {
                             sessionRepository.delete(session);
                             return Void.TYPE; }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                                                               "Session not found"));
    }


    public SessionResponseDto findSessionBySessionCode(String sessionCode) {
        Session session = findBySessionCode(sessionCode);
        return SessionResponseDto.toSessionResponseDto(session);
    }


    private Session findBySessionCode(String sessionCode) {
        return sessionRepository.findBySessionCode(sessionCode)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Session not found"));

    }

}
