package ru.rvukolov.moneytransferbackend.service;

import org.apache.logging.log4j.util.PropertySource;
import org.springframework.stereotype.Service;
import ru.rvukolov.moneytransferbackend.model.Operation;
import ru.rvukolov.moneytransferbackend.repository.OperationsRepository;

import java.util.Comparator;
import java.util.List;
@Service
public class OperationsService {

    private OperationsRepository operationsRepository;

    public OperationsService(OperationsRepository operationsRepository) {
        this.operationsRepository = operationsRepository;
    }

    public List<Operation> getOperations() {
        return operationsRepository.getOperations().values().stream()
                .sorted(Comparator.reverseOrder())
                .toList();
    }
}
