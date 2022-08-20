package ru.rvukolov.moneytransferbackend.exceptions;

import ru.rvukolov.moneytransferbackend.model.Operation;

public class CardException extends RuntimeException{

    private Operation operation;

    public CardException(String message, String cardId, Operation operation) {
        super(message + ": " + cardId);
        this.operation = operation;
    }

    public Operation getOperation() {
        return operation;
    }
}

