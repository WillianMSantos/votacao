package com.cooperativismo.votacao.controller;


import com.cooperativismo.votacao.dto.response.VoteResultResponseDto;
import com.cooperativismo.votacao.service.VotingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Voting")
@RequestMapping("v1/api/voting")
public class VotingController {


    private VotingService votingService;

    @Autowired
    public VotingController(VotingService votingService) {
        this.votingService = votingService;
    }

    @ApiOperation(value = "Result vote", response = VoteResultResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Result vote")
    })
    @GetMapping("/{id}/votacao")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findVotesByScheduleId(@PathVariable String id) {
        VoteResultResponseDto voteResultResponseDto = this.votingService.buildVoteSchedule(id);
        return ResponseEntity.ok(voteResultResponseDto);
    }
}
