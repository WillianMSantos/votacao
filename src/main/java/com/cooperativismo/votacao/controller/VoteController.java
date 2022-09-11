package com.cooperativismo.votacao.controller;


import com.cooperativismo.votacao.dto.request.VoteRequestDto;
import com.cooperativismo.votacao.model.Vote;
import com.cooperativismo.votacao.service.VoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Api(value = "Vote")
@RequestMapping(value = "v1/api/vote", produces = "application/json")
public class VoteController {


    private VoteService voteService;
    @Autowired
    public VoteController(VoteService voteService){this.voteService = voteService;}

    @ApiOperation(value = "Create vote", response = Vote.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Vote successfully")
    })
    @PostMapping("/schedule/{idSchedule}/sessions/{idSession}/votes")
    @ResponseStatus(HttpStatus.CREATED)
    public Vote createVote(@PathVariable String idSchedule, @PathVariable String idSession,
                            @RequestBody VoteRequestDto voteRequestDto) {
        return voteService.createVote(idSchedule, idSession, voteRequestDto);
    }

    @ApiOperation(value = "List votes", response = Vote.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Votes found")
    })
    @GetMapping("/list-votes")
    public ResponseEntity<?> all() {
        return ResponseEntity.ok(this.voteService.findAll());
    }

    @ApiOperation(value = "Find vote", response = Vote.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Vote found")
    })
    @GetMapping("/vote/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){
        return ResponseEntity.ok(this.voteService.findById(id));
    }


    @ApiOperation(value = "Delete vote")
    @DeleteMapping("/delete-vote/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(String id) {
        voteService.delete(id);
    }

}
