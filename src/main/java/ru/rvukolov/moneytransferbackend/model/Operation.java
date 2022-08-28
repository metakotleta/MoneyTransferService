package ru.rvukolov.moneytransferbackend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.uuid.Generators;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Operation implements Comparable<Operation>{
    private UUID operationId;
    private OperationTypes operationType;
    private OperationStatuses operationStatus;
    private TransferRequest request;
    private Card card;
    private Instant operationTime;

    public Operation(OperationTypes operationType) {
        this.operationId = Generators.timeBasedGenerator().generate();
        this.operationType = operationType;
        this.operationTime = Instant.now();
    }

    public Operation(OperationTypes operationType, Card card) {
        this.operationId = Generators.timeBasedGenerator().generate();
        this.operationType = operationType;
        this.card = card;
        this.operationTime = Instant.now();
    }
    public Operation setOperationStatus(OperationStatuses operationStatus) {
        this.operationStatus = operationStatus;
        return this;
    }

    public Operation setRequest(TransferRequest request) {
        this.request = request;
        return this;
    }


    @Override
    public int compareTo(Operation o) {
        return this.operationId.compareTo(o.operationId);
    }
}
