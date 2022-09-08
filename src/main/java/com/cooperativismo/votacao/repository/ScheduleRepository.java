package com.cooperativismo.votacao.repository;

import com.cooperativismo.votacao.model.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ScheduleRepository extends MongoRepository<Schedule, String> {

    Optional<Schedule> findByCodeSchedule(String codeSchedule);
}
