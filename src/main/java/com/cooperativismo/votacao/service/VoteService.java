package com.cooperativismo.votacao.service;

import com.cooperativismo.votacao.dto.request.VoteRequestDto;
import com.cooperativismo.votacao.dto.response.CpfValidationDto;
import com.cooperativismo.votacao.dto.response.VoteResultResponseDto;
import com.cooperativismo.votacao.exception.*;
import com.cooperativismo.votacao.kafka.KafkaSender;
import com.cooperativismo.votacao.model.Schedule;
import com.cooperativismo.votacao.model.Session;
import com.cooperativismo.votacao.model.Vote;
import com.cooperativismo.votacao.repository.ScheduleRepository;
import com.cooperativismo.votacao.repository.VoteRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class VoteService {

    private static final String CPF_UNABLE_TO_VOTE = "UNABLE_TO_VOTE";

    private VoteRepository voteRepository;

    private RestTemplate restTemplate;

    private ScheduleService scheduleService;

    private SessionService sessionService;

    private KafkaSender kafkaSender;

    private VotingService votingService;


    @Value("${cpf.url}")
    private String cpfUrl = "";

    @Autowired
    public VoteService(RestTemplate restTemplate, VoteRepository voteRepository,
                       SessionService sessionService, VotingService votingService,
                       ScheduleService scheduleService) {

        this.restTemplate = restTemplate;
        this.voteRepository = voteRepository;
        this.sessionService = sessionService;
        this.votingService = votingService;
        this.scheduleService = scheduleService;
    }


    public Vote createVote(String idSchedule, String idSession, VoteRequestDto voteRequestDto) {

        val session = sessionService.findById(idSession);
        val schedule = scheduleService.findById(idSchedule);

        if(!schedule.getId().equals(session.getSchedule().getId())) {
            throw new InvalidSessionException();
        }

        val vote = new Vote();

        vote.setId(voteRequestDto.getId());
        vote.setCpf(voteRequestDto.getCpf());
        vote.setVote(voteRequestDto.getVote());
        vote.setSchedule(session.getSchedule());

        return verifyAndSave(session, vote);
    }

    private Vote verifyAndSave(final Session session, final Vote vote) {
        verifyVote(session, vote);
        return voteRepository.save(vote);
    }

    public void verifyVote(Session session, Vote vote) {

        val limitDate = session.getStartDate().plusMinutes(session.getExpiryMinutes());
        if (LocalDateTime.now().isAfter(limitDate)) {
            sendMessage(vote.getSchedule());
            throw new SessionTimeOutException();
        }

        cpfAbleForVote(vote);
        checkIfVoteAlreadyExists(vote);
    }


    public void checkIfVoteAlreadyExists(Vote vote) {

        val voteByCpfAndSchedule = voteRepository.findByCpfAndScheduleId(vote.getCpf(), vote.getId());

        if(voteByCpfAndSchedule.isPresent()){
            throw new VoteAlreadyExistsException();
        }

    }


    private void sendMessage(Schedule schedule) {
        VoteResultResponseDto voteResultResponseDto = votingService.buildVoteSchedule(schedule.getId());
        //kafkaSender.sendMessage(voteResultResponseDto);
    }

    protected void cpfAbleForVote(Vote vote) {
        val cpfValidation = getCpfValidation(vote);

        if(HttpStatus.OK.equals(cpfValidation.getStatusCode())){
            if(CPF_UNABLE_TO_VOTE.equalsIgnoreCase(cpfValidation.getBody().getStatus())) {
                throw new CpfUnableException();
            }
        }else {
            throw new CpfInvalidException();
        }
    }

    private ResponseEntity<CpfValidationDto> getCpfValidation(final Vote vote) {
        val headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        val entity  = new HttpEntity<>(headers);

        return restTemplate.exchange(cpfUrl.concat(vote.getCpf()),
                HttpMethod.GET, entity, CpfValidationDto.class);
    }


    public List<Vote> findAll() {
        return voteRepository.findAll();
    }


    public Vote findById(String id) {
        return voteRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "vote not found"));
    }


    public List<Vote> findByScheduleId(String id){

        return voteRepository.findByScheduleId(id)
                             .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                     "vote not found"));
    }


    public void delete(String id) {
        voteRepository.findById(id)
                .map(vote -> {
                    voteRepository.delete(vote);
                    return Void.TYPE;}).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Vote not found"));
    }

}
