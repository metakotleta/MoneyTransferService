package ru.rvukolov.moneytransferbackend.exceptions;

import ru.rvukolov.moneytransferbackend.model.Card;
import ru.rvukolov.moneytransferbackend.model.Operation;

public class CardNotFoundException extends RuntimeException{

    private String cardId;
    private Operation operation;

    public CardNotFoundException(String message, String cardId, Operation operation) {
        super(message + ": " + cardId);
        this.cardId = cardId;
        this.operation = operation;
    }

    public String getCard() {
        return cardId;
    }

    public Operation getOperation() {
        return operation;
    }
}

