package com.cooperativismo.votacao.exception;

public class ExistingSessionException extends RuntimeException {
    public ExistingSessionException(){ super("Session is present");}
}
