package ru.rvukolov.moneytransferbackend.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.rvukolov.moneytransferbackend.model.Operation;
import ru.rvukolov.moneytransferbackend.model.OperationStatuses;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Getter
@NoArgsConstructor
public class OperationsRepository {

    private Map<UUID, Operation> operations = new ConcurrentHashMap<>();

    public void addOperation(Operation operation) {
        operations.put(operation.getOperationId(), operation);
    }

    public Operation getOperationById(UUID id) {
        return operations.get(id);
    }

    public boolean containOperation(UUID id) {
        return operations.containsKey(id);
    }

    public boolean isNotConfirmedOperation(UUID id) {
        return operations.get(id).getOperationStatus() != OperationStatuses.SUCCESS_CONFIRMED;
    }
}
