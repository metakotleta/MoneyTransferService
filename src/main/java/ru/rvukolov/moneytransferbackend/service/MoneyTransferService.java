package ru.rvukolov.moneytransferbackend.service;

import org.springframework.stereotype.Service;
import ru.rvukolov.moneytransferbackend.exceptions.CardException;
import ru.rvukolov.moneytransferbackend.model.Operation;
import ru.rvukolov.moneytransferbackend.model.TransferRequest;
import ru.rvukolov.moneytransferbackend.repository.CardsRepository;
import ru.rvukolov.moneytransferbackend.repository.OperationsRepository;

@Service
public class MoneyTransferService {

    private OperationsRepository operationsRepository;
    private CardsRepository cardsRepository;

    public MoneyTransferService(OperationsRepository operationsRepository, CardsRepository cardsRepository) {
        this.operationsRepository = operationsRepository;
        this.cardsRepository = cardsRepository;
    }

    public Operation transfer(TransferRequest request) {
        try {
            return cardsRepository.transfer(request);
        } catch (CardException e) {
            operationsRepository.getOperations().put(e.getOperation().getOperationId(), e.getOperation());
            throw e;
        }
    }

}
