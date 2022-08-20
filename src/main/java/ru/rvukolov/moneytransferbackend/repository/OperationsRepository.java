package ru.rvukolov.moneytransferbackend.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.rvukolov.moneytransferbackend.model.Operation;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Getter
@NoArgsConstructor
public class OperationsRepository {

    private Map<UUID, Operation> operations = new ConcurrentHashMap<>();

}
