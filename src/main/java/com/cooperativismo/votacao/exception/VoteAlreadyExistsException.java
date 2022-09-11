package com.cooperativismo.votacao.exception;

import org.springframework.http.HttpStatus;

public class VoteAlreadyExistsException extends BusinessException {

    public VoteAlreadyExistsException(){super("Vote already exists", HttpStatus.ALREADY_REPORTED);}
}
