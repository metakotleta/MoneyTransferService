package ru.rvukolov.moneytransferbackend.model.out;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import ru.rvukolov.moneytransferbackend.model.Operation;

import java.util.Arrays;

@Getter
public class FailResponse extends Response {
    private String reason;

    public FailResponse(Throwable e, Operation operation) {
        super.setMessage(e.getMessage());
        super.setOperation(operation);
        this.reason = Arrays.stream(e.getStackTrace()).toList().toString();
    }
}
