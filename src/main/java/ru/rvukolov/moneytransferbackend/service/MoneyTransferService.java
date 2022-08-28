package ru.rvukolov.moneytransferbackend.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.rvukolov.moneytransferbackend.exceptions.CardException;
import ru.rvukolov.moneytransferbackend.model.*;
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
        Operation operation = new Operation(OperationTypes.TRANSFER);
        Card senderCard = cardsRepository.checkCardExists(request.getCardFromNumber(), operation);
        Card receiverCard = cardsRepository.checkCardExists(request.getCardToNumber(), operation);
        double amount = request.getAmount().getValue();
        if (cardsRepository.checkSenderCard(senderCard,request)) {
            senderCard.spendBalance(amount);
            receiverCard.addBalance(amount);
            operation.setOperationStatus(OperationStatuses.SUCCESS).setRequest(request);
            operationsRepository.addOperation(operation);
        } else {
            operation.setOperationStatus(OperationStatuses.FAIL).setRequest(request);
            operationsRepository.addOperation(operation);
            throw new CardException("Incorrect card data:" + request.getCardFromNumber(), operation, HttpStatus.NOT_FOUND);
        }
        return operation;
    }

}
