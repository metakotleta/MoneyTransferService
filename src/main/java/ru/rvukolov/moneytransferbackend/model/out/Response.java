package ru.rvukolov.moneytransferbackend.model.out;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.rvukolov.moneytransferbackend.model.Operation;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private Operation operation;
    private String message;

    public Response(Operation operation) {
        this.operation = operation;
    }
}

