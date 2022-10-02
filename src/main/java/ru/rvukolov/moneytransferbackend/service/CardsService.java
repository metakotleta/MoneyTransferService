package ru.rvukolov.moneytransferbackend.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.rvukolov.moneytransferbackend.exceptions.CardException;
import ru.rvukolov.moneytransferbackend.exceptions.UserAlreadyRegisteredException;
import ru.rvukolov.moneytransferbackend.model.*;
import ru.rvukolov.moneytransferbackend.repository.CardsRepository;
import ru.rvukolov.moneytransferbackend.repository.OperationsRepository;

@Service
public class CardsService {
    private final OperationsRepository operationsRepository;
    private final CardsRepository cardsRepository;

    public CardsService(OperationsRepository operationsRepository, CardsRepository cardsRepository) {
        this.operationsRepository = operationsRepository;
        this.cardsRepository = cardsRepository;
    }

    public Operation getCardById(String cardId) {
        Operation operation;
        if (cardsRepository.hasCard(cardId)) {
            Card card = cardsRepository.getCardById(cardId);
            operation = new Operation(OperationTypes.GET_CARD, card.getDto(), OperationStatuses.SUCCESS);
            operationsRepository.addOperation(operation);
        } else {
            operation = new Operation(OperationTypes.GET_CARD, OperationStatuses.FAIL);
            operationsRepository.addOperation(operation);
            throw new CardException("Card not found: " + cardId, operation, HttpStatus.NOT_FOUND);
        }
        return operation;
    }

    public Operation addCard(Card card) {
        Operation operation;
        if (!cardsRepository.getCards().containsKey(card.getCardId())) {
            cardsRepository.addCard(card);
            operation = new Operation(OperationTypes.ADD_CARD, card.getDto(), OperationStatuses.SUCCESS);
            operationsRepository.addOperation(operation);
        } else {
            operation = new Operation(OperationTypes.ADD_CARD, card.getDto(), OperationStatuses.FAIL);
            operationsRepository.addOperation(operation);
            throw new UserAlreadyRegisteredException("User already Registered", operation, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return operation;
    }

    public synchronized Operation addBalance(String cardNumber, Amount amount) {
        Operation operation;
        if (cardsRepository.hasCard(cardNumber)) {
            Card card = cardsRepository.getCardById(cardNumber);
            card.addBalance(amount.getValue());
            operation = new Operation(OperationTypes.ADD_BALANCE, card.getDto(), OperationStatuses.SUCCESS);

            operationsRepository.addOperation(operation);
        } else {
            operation = new Operation(OperationTypes.ADD_BALANCE, OperationStatuses.FAIL);
            operationsRepository.addOperation(operation);
            throw new CardException("Card not found: " + cardNumber, operation, HttpStatus.NOT_FOUND);
        }
        return operation;
    }
}
