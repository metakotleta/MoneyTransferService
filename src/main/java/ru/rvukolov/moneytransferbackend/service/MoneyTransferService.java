package ru.rvukolov.moneytransferbackend.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.rvukolov.moneytransferbackend.exceptions.CardException;
import ru.rvukolov.moneytransferbackend.exceptions.OperationException;
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
        double amount = request.getAmount().getValue();
        if (cardsRepository.checkSenderCard(senderCard,request)) {
            senderCard.reserveSpendBalance(operation.getOperationId(), amount);
            operation.setOperationStatus(OperationStatuses.CONFIRMATION_WAIT).setRequest(request);
            operationsRepository.addOperation(operation);
        } else {
            operation.setOperationStatus(OperationStatuses.FAIL).setRequest(request);
            operationsRepository.addOperation(operation);
            throw new CardException("Incorrect card data:" + request.getCardFromNumber(), operation, HttpStatus.NOT_FOUND);
        }
        return operation;
    }

    public Operation confirmOperation(Confirm confirm) {
        //TODO: добавить ДТО для корректной передачи в /operations (менять статус и тип исходной операции
        var operation = new ConfirmOperation(OperationTypes.TRANSFER_CONFIRM);
        if (operationsRepository.containOperation(confirm.getOperationId())) {
            Operation transferOperation = operationsRepository.getOperationById(confirm.getOperationId());
            Card senderCard = cardsRepository.getCardById(transferOperation.getRequest().getCardFromNumber());
            Card receiverCard = cardsRepository.getCardById(transferOperation.getRequest().getCardToNumber());
            double spendAmount = senderCard.getReservedBalance().get(transferOperation.getOperationId());
            senderCard.spendBalance(spendAmount);
            receiverCard.addBalance(spendAmount);
            operation.setConfirmedOperation(transferOperation);
            operationsRepository.addOperation(operation);
        } else {
            operation.setOperationStatus(OperationStatuses.FAIL);
            operationsRepository.addOperation(operation);
            throw new OperationException("Operation not found.", operation, HttpStatus.NOT_FOUND);
        }
        return operation;
    }

}
