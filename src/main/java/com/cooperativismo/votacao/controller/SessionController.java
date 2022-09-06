package com.cooperativismo.votacao.controller;

import com.cooperativismo.votacao.dto.request.SessionRequestDto;
import com.cooperativismo.votacao.dto.response.SessionResponseDto;
import com.cooperativismo.votacao.service.SessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(value = "Session")
@RequestMapping("v1/api/session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @ApiOperation(value = "Create session")
    @PostMapping("/{codeSchedule}/create-session")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createSession(@PathVariable String codeSchedule, @Valid @RequestBody SessionRequestDto sessionRequestDto) {
        sessionService.createSession(codeSchedule, sessionRequestDto);
    }

    @ApiOperation(value = "List sessions")
    @GetMapping("/list-sessions")
    @ResponseStatus(code = HttpStatus.OK)
    public List<SessionResponseDto> listAll(){
        return sessionService.findAll();
    }

    @ApiOperation(value = "Delete session")
    @DeleteMapping("/delete-session/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable String id){
        sessionService.deleteSession(id);
    }

    @ApiOperation(value = "Find session")
    @GetMapping("/{id}/find-session")
    @ResponseStatus(code = HttpStatus.OK)
    public SessionResponseDto findSession(@PathVariable String id) {
        return sessionService.findSessionBySessionCode(id);
    }

}
