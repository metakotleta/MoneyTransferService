package ru.rvukolov.moneytransferbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.rvukolov.moneytransferbackend.model.Operation;
import ru.rvukolov.moneytransferbackend.model.OperationTypes;
import ru.rvukolov.moneytransferbackend.model.out.Response;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class UserAlreadyRegisteredException extends AApplicationException {

    public UserAlreadyRegisteredException(String message, Operation operation, HttpStatus status) {
        super(message + ": " + operation.getCard().getCardId(), operation, status);
    }
}