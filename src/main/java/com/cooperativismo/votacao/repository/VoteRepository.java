package com.cooperativismo.votacao.repository;

import com.cooperativismo.votacao.model.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends MongoRepository<Vote, String> {

    Optional<Vote> findByCpf(String cpf);

    Optional<Vote> findById(String id);

    Optional<List<Vote>> findByScheduleId(String id);

    Optional<Vote> findByCpfAndScheduleId(String cpf, String id);

}
