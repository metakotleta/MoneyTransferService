package ru.rvukolov.moneytransferbackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.rvukolov.moneytransferbackend.exceptions.CardException;
import ru.rvukolov.moneytransferbackend.exceptions.UserAlreadyRegisteredException;
import ru.rvukolov.moneytransferbackend.model.*;
import ru.rvukolov.moneytransferbackend.repository.CardsRepository;
import ru.rvukolov.moneytransferbackend.repository.OperationsRepository;

@Service
public class CardsService {

    private static Logger log = LoggerFactory.getLogger(CardsService.class);
    private OperationsRepository operationsRepository;
    private CardsRepository cardsRepository;

    public CardsService(OperationsRepository operationsRepository, CardsRepository cardsRepository) {
        this.operationsRepository = operationsRepository;
        this.cardsRepository = cardsRepository;
    }

    public Operation getCardById(String cardId) {
        //   log.info("Getting card by id, requsted card: {}", cardId);
        Operation operation;
        if (cardsRepository.hasCard(cardId)) {
            Card card = cardsRepository.getCardById(cardId);
            operation = new Operation(OperationTypes.GET_CARD, card).setOperationStatus(OperationStatuses.SUCCESS);
            operationsRepository.addOperation(operation);
        } else {
            operation = new Operation(OperationTypes.GET_CARD).setOperationStatus(OperationStatuses.FAIL);
            operationsRepository.addOperation(operation);
            throw new CardException("Card not found: " + cardId, operation, HttpStatus.NOT_FOUND);
        }
        return operation;
    }

    public Operation addCard(Card card) {
        Operation operation;
        if (!cardsRepository.getCards().containsKey(card.getCardId())) {
            cardsRepository.addCard(card);
            operation = new Operation(OperationTypes.ADD_CARD, card).setOperationStatus(OperationStatuses.SUCCESS);
            operationsRepository.addOperation(operation);
        } else {
            operation = new Operation(OperationTypes.ADD_CARD, card).setOperationStatus(OperationStatuses.FAIL);
            operationsRepository.addOperation(operation);
            throw new UserAlreadyRegisteredException("User already Registered", operation, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return operation;
    }

    public Operation addBalance(String cardNumber, Amount amount) {
        Operation operation;
        if (cardsRepository.hasCard(cardNumber)) {
            Card card = cardsRepository.getCardById(cardNumber);
            cardsRepository.addBalance(card, amount.getValue());
            operation = new Operation(OperationTypes.ADD_BALANCE, card).setOperationStatus(OperationStatuses.SUCCESS);
            operationsRepository.addOperation(operation);
        } else {
            operation = new Operation(OperationTypes.ADD_BALANCE).setOperationStatus(OperationStatuses.FAIL);
            operationsRepository.addOperation(operation);
            throw new CardException("Card not found: " + cardNumber, operation, HttpStatus.NOT_FOUND);
        }
        return operation;
    }
}
