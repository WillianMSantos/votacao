package com.cooperativismo.votacao.repository;

import org.bson.types.ObjectId;

public class IdGeneratorImpl implements IdGenerator {

    @Override
    public String generateId() {
        return new ObjectId().toString();
    }
}
