package com.cooperativismo.votacao.service;


import com.cooperativismo.votacao.dto.response.VoteResultResponseDto;
import com.cooperativismo.votacao.exception.BusinessException;
import com.cooperativismo.votacao.exception.VotingNotFoundException;
import com.cooperativismo.votacao.model.Schedule;
import com.cooperativismo.votacao.model.Vote;
import com.cooperativismo.votacao.repository.SessionRepository;
import com.cooperativismo.votacao.repository.VoteRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class VotingService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private SessionRepository sessionRepository;


    public VoteResultResponseDto getResultVoting(String id) {
        val votingSchedule = buildVoteSchedule(id);
        return votingSchedule;
    }


    public VoteResultResponseDto buildVoteSchedule(String id) {

        val votesBySchedule = voteRepository.findByScheduleId(id);
        if(!votesBySchedule.isPresent() || votesBySchedule.get().isEmpty()){
            throw new VotingNotFoundException();
        }

        Schedule schedule = votesBySchedule.get().iterator().next().getSchedule();

        val totalSessions = sessionRepository.countByScheduleId(schedule.getId());
        val total = votesBySchedule.get().size();
        val totalYes = calculateVotesYes(votesBySchedule);
        val totalNo = total - totalYes;

        return VoteResultResponseDto.builder()
                .schedule(schedule)
                .totalVotes(total)
                .totalSessions(totalSessions.intValue())
                .totalYes(totalYes)
                .totalNo(totalNo)
                .build();
    }

    private Integer calculateVotesYes(Optional<List<Vote>> votesSchedule) {

        return (int) votesSchedule.get()
                                   .stream()
                                   .filter(vote -> Boolean.TRUE.equals(vote.getVote()))
                                   .count();
    }
}

