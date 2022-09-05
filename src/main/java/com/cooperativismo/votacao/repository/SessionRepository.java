package com.cooperativismo.votacao.repository;

import com.cooperativismo.votacao.model.Session;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SessionRepository extends MongoRepository<Session, String> {

    Optional<Session> findBySessionCode(String sessionCode);

}
