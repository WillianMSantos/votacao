package com.cooperativismo.votacao.exception;

public class VoteAlreadyExistsException extends RuntimeException {

    public VoteAlreadyExistsException(){super("Vote already exists");}
}
