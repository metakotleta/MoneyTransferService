package ru.rvukolov.moneytransferbackend.service;

import org.springframework.stereotype.Service;
import ru.rvukolov.moneytransferbackend.exceptions.CardException;
import ru.rvukolov.moneytransferbackend.exceptions.UserAlreadyRegisteredException;
import ru.rvukolov.moneytransferbackend.model.Amount;
import ru.rvukolov.moneytransferbackend.model.Card;
import ru.rvukolov.moneytransferbackend.model.Operation;
import ru.rvukolov.moneytransferbackend.repository.CardsRepository;
import ru.rvukolov.moneytransferbackend.repository.OperationsRepository;

@Service
public class CardsService {

    private OperationsRepository operationsRepository;
    private CardsRepository cardsRepository;

    public CardsService(OperationsRepository operationsRepository, CardsRepository cardsRepository) {
        this.operationsRepository = operationsRepository;
        this.cardsRepository = cardsRepository;
    }

    public Operation getCardById(String cardId) {
        try {
            var operation = cardsRepository.getCardById(cardId);
            operationsRepository.getOperations().put(operation.getOperationId(), operation);
            return operation;
        } catch (CardException e) {
            operationsRepository.getOperations().put(e.getOperation().getOperationId(), e.getOperation());
            throw e;
        }
    }

    public Operation addCard(Card card) {
        try {
            var operation = cardsRepository.addCard(card);
            operationsRepository.getOperations().put(operation.getOperationId(), operation);
            return operation;
        } catch (UserAlreadyRegisteredException e) {
            operationsRepository.getOperations().put(e.getOperation().getOperationId(), e.getOperation());
            throw e;
        }
    }

    public Operation addBalance(String cardNumber, Amount amount) {
        try {
            var operation = cardsRepository.addBalance(cardNumber, amount.getValue());
            operationsRepository.getOperations().put(operation.getOperationId(), operation);
            return operation;
        } catch (CardException e) {
            operationsRepository.getOperations().put(e.getOperation().getOperationId(), e.getOperation());
            throw e;
        }

    }
}
