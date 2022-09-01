package ru.rvukolov.moneytransferbackend.model.out;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.rvukolov.moneytransferbackend.model.Operation;
import ru.rvukolov.moneytransferbackend.model.OperationStatuses;
import ru.rvukolov.moneytransferbackend.model.OperationTypes;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Accessors(chain = true)
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private final UUID operationId;
    private final OperationTypes operationType;
    private final OperationStatuses operationStatus;
    private final Instant operationTime;
    private ValidationError validationError;
    private String cardId;
    private CardDto cardDto;
    private String message;

    public Response(Operation operation) {
        this.operationId = operation.getOperationId();
        this.operationType = operation.getOperationType();
        this.operationStatus = operation.getOperationStatus();
        this.operationTime = operation.getOperationTime();
    }
}

