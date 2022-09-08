package com.cooperativismo.votacao.controller;


import com.cooperativismo.votacao.dto.request.VoteRequestDto;
import com.cooperativismo.votacao.model.Vote;
import com.cooperativismo.votacao.service.VoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "Vote")
@RequestMapping("v1/api/vote")
public class VoteController {

    @Autowired
    private VoteService voteService;


    @ApiOperation(value = "Create vote")
    @PostMapping("/schedule/{idSchedule}/sessions/{idSession}/votes")
    @ResponseStatus(HttpStatus.CREATED)
    public Vote createVote(@PathVariable String idSchedule, @PathVariable String idSession,
                            @RequestBody VoteRequestDto voteRequestDto) {
        return voteService.createVote(idSchedule, idSession, voteRequestDto);
    }

    @ApiOperation(value = "List votes")
    @GetMapping("/list-votes")
    @ResponseStatus(HttpStatus.OK)
    public List<Vote> all() {
        return voteService.findAll();
    }

    @ApiOperation(value = "Find vote")
    @GetMapping("/vote/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Vote findById(@PathVariable String id){
        return voteService.findById(id);
    }


    @ApiOperation(value = "Delete vote")
    @DeleteMapping("/delete-vote/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(String id) {
        voteService.delete(id);
    }

}
