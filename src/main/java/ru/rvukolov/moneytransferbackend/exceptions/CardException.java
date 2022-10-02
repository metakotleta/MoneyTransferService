package ru.rvukolov.moneytransferbackend.exceptions;

import org.springframework.http.HttpStatus;
import ru.rvukolov.moneytransferbackend.model.Operation;


public class CardException extends AApplicationException{

    public CardException(String message, Operation operation, HttpStatus status) {
        super(message, operation, status);
    }
}

