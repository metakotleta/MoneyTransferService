package ru.rvukolov.moneytransferbackend.model.out;

import ru.rvukolov.moneytransferbackend.model.Operation;

import java.util.Arrays;

public class FailResponse extends Response {
    public FailResponse(Throwable e, Operation operation) {
        super.setMessage(e.getMessage());
        super.setReason(Arrays.stream(e.getStackTrace()).toList().toString());
        super.setOperation(operation);
    }
}
