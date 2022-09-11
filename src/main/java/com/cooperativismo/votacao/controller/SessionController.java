package com.cooperativismo.votacao.controller;

import com.cooperativismo.votacao.dto.request.SessionRequestDto;
import com.cooperativismo.votacao.dto.response.SessionResponseDto;
import com.cooperativismo.votacao.service.SessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@Api(value = "Session")
@RequestMapping("v1/api/session")
public class SessionController {

    private SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @ApiOperation(value = "Create session", response = SessionResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Session successfully created")
    })
    @PostMapping("/{id}/create-session")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> createSession(@PathVariable String id, @Valid @RequestBody SessionRequestDto sessionRequestDto) throws URISyntaxException {
        SessionResponseDto sessionResponseDto = this.sessionService.createSession(id, sessionRequestDto);
        return ResponseEntity.created(new URI(sessionResponseDto.getId())).body(sessionResponseDto);
    }

    @ApiOperation(value = "List sessions", response = SessionResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sessions fund.")
    })
    @GetMapping("/list-sessions")
    public ResponseEntity<?> listAll(){
        return ResponseEntity.ok(this.sessionService.findAll());
    }

    @ApiOperation(value = "Delete session")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Session deleted.")
    })
    @DeleteMapping("/delete-session/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable String id){
        sessionService.deleteSession(id);
    }

    @ApiOperation(value = "Find session", response = SessionResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Session found.")
    })
    @GetMapping("/{id}/find-session")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> findSession(@PathVariable String id) {
        return ResponseEntity.ok(this.sessionService.findSessionBySessionCode(id));
    }

}
