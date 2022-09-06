package com.cooperativismo.votacao.controller;


import com.cooperativismo.votacao.dto.response.VoteResultResponseDto;
import com.cooperativismo.votacao.service.VotingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Voting")
@RequestMapping("v1/api/voting")
public class VotingController {


    @Autowired
    private VotingService votingService;


    @ApiOperation(value = "Result vote")
    @GetMapping("/{id}/votacao")
    @ResponseStatus(HttpStatus.OK)
    public VoteResultResponseDto findVotesByScheduleId(@PathVariable String id) {
        return votingService.getResultVoting(id);
    }
}
