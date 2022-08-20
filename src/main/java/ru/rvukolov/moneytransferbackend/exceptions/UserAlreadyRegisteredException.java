package ru.rvukolov.moneytransferbackend.exceptions;

import ru.rvukolov.moneytransferbackend.model.Operation;
import ru.rvukolov.moneytransferbackend.model.OperationTypes;

public class UserAlreadyRegisteredException extends RuntimeException {

    private Operation operation;

    public UserAlreadyRegisteredException(String message, Operation operation) {
        super(message);
        this.operation = operation;
    }

    public Operation getOperation() {
        return operation;
    }
}
