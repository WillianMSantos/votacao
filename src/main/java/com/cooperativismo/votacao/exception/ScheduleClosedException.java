package com.cooperativismo.votacao.exception;

public class ScheduleClosedException extends RuntimeException {
    public ScheduleClosedException(){ super("Schedule is closed");}
}
