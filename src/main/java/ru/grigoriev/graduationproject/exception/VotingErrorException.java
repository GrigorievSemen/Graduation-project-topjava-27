package ru.grigoriev.graduationproject.exception;

public class VotingErrorException extends RuntimeException {
    public VotingErrorException(String message) {
        super(message);
    }
}
