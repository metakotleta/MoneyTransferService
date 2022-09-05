package ru.rvukolov.moneytransferbackend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.uuid.Generators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.rvukolov.moneytransferbackend.model.out.CardDto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
public class Operation implements Comparable<Operation>{
    private UUID operationId;
    private OperationTypes operationType;
    private OperationStatuses operationStatus;

    private TransferRequest request;
    private CardDto card;
    private Instant operationTime;

    public Operation(OperationTypes operationType) {
        this.operationId = Generators.timeBasedGenerator().generate();
        this.operationType = operationType;
        this.operationTime = Instant.now();
    }

    public Operation(OperationTypes operationType, Card card) {
        this.operationId = Generators.timeBasedGenerator().generate();
        this.operationType = operationType;
        this.card = new CardDto(card);
        this.operationTime = Instant.now();
    }

    @Override
    public int compareTo(Operation o) {
        return this.operationId.compareTo(o.operationId);
    }
}
