package ru.rvukolov.moneytransferbackend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.uuid.Generators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import ru.rvukolov.moneytransferbackend.model.out.CardDto;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Operation implements Comparable<Operation>{
    private final UUID operationId;
    private final OperationTypes operationType;
    private OperationStatuses operationStatus;
    private final TransferRequest request;
    private final CardDto card;
    private final Instant operationTime;

    public Operation(OperationTypes operationType, OperationStatuses operationStatus,
                     @Nullable TransferRequest request, @Nullable CardDto card, Instant operationTime) {
        this.operationId = Generators.timeBasedGenerator().generate();
        this.operationType = operationType;
        this.operationStatus = operationStatus;
        this.request = request;
        this.card = card;
        this.operationTime = operationTime;
    }

    public Operation(OperationTypes operationType) {
        this.operationId = Generators.timeBasedGenerator().generate();
        this.operationType = operationType;
        this.operationStatus = null;
        this.card = null;
        this.request = null;
        this.operationTime = Instant.now();

    }

    public Operation(OperationTypes operationType, OperationStatuses operationStatus) {
        this.operationId = Generators.timeBasedGenerator().generate();
        this.operationType = operationType;
        this.operationStatus = operationStatus;
        this.operationTime = Instant.now();
        this.card = null;
        this.request = null;
    }

    public Operation(OperationTypes operationType, OperationStatuses operationStatus, @Nullable TransferRequest request) {
        this.operationId = Generators.timeBasedGenerator().generate();
        this.operationType = operationType;
        this.operationStatus = operationStatus;
        this.request = request;
        this.card = null;
        this.operationTime = Instant.now();

    }

    public Operation(OperationTypes operationType, CardDto card, OperationStatuses operationStatus) {
        this.operationId = Generators.timeBasedGenerator().generate();
        this.operationType = operationType;
        this.operationStatus = operationStatus;
        this.request = null;
        this.card = card;
        this.operationTime = Instant.now();
    }

    public void setOperationStatus(OperationStatuses operationStatus) {
        this.operationStatus = operationStatus;
    }
    @Override
    public int compareTo(Operation o) {
        return this.operationId.compareTo(o.operationId);
    }
}
