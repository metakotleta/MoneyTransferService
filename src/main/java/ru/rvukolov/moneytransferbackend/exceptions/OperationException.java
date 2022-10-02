package ru.rvukolov.moneytransferbackend.exceptions;

import org.springframework.http.HttpStatus;
import ru.rvukolov.moneytransferbackend.model.Operation;

public class OperationException extends AApplicationException {

    public OperationException(String message, Operation operation, HttpStatus status) {
        super(message, operation, status);
    }

    public OperationException(String message, Operation operation, HttpStatus status, Throwable e) {
        super(message, operation, status, e);
    }
}
