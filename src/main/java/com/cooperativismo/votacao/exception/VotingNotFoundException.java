package com.cooperativismo.votacao.exception;

public class VotingNotFoundException extends RuntimeException {
    public VotingNotFoundException(){super("Voting not found");}
}
