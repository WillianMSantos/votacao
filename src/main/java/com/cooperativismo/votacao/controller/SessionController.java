package com.cooperativismo.votacao.controller;

import com.cooperativismo.votacao.dto.request.SessionRequestDto;
import com.cooperativismo.votacao.dto.response.SessionResponseDto;
import com.cooperativismo.votacao.service.SessionService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/api/session")
public class SessionController {

    @Autowired
    private SessionService sessionService;


    @PostMapping("/{codeSchedule}/create-session")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createSession(@PathVariable String codeSchedule, @Valid @RequestBody SessionRequestDto sessionRequestDto) {
        sessionService.createSession(codeSchedule, sessionRequestDto);
    }

    @GetMapping("/list-sessions")
    @ResponseStatus(code = HttpStatus.OK)
    public List<SessionResponseDto> listAll(){
        return sessionService.findAll();
    }

    @DeleteMapping("/delete-session/{sessionCode}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable String sessionCode){
        sessionService.deleteSession(sessionCode);
    }

    @GetMapping("/{sessionCode}/find-session")
    @ResponseStatus(code = HttpStatus.OK)
    public SessionResponseDto findSession(@PathVariable String sessionCode) {
        return sessionService.findSessionBySessionCode(sessionCode);
    }

}
