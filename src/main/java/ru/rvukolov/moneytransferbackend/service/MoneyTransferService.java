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
    public static final String CODE = "0000";
    private OperationsRepository operationsRepository;
    private CardsRepository cardsRepository;

    public MoneyTransferService(OperationsRepository operationsRepository, CardsRepository cardsRepository) {
        this.operationsRepository = operationsRepository;
        this.cardsRepository = cardsRepository;
    }

    //только резервируем баланс
    public Operation transfer(TransferRequest request) {
        double amount = request.getAmount().getValue();
        var operation = new Operation(OperationTypes.TRANSFER, OperationStatuses.CONFIRMATION_WAIT, request);
        Card senderCard = cardsRepository.checkCardExists(request.getCardFromNumber(), request);
        cardsRepository.checkCardExists(request.getCardToNumber(), request);
        if (cardsRepository.checkSenderCard(senderCard, request)) {
            senderCard.reserveSpendBalance(operation.getOperationId(), amount);
            operationsRepository.addOperation(operation);
            return operation;
        } else {
            var operationFail = new Operation(OperationTypes.TRANSFER, OperationStatuses.FAIL, request);
            operationsRepository.addOperation(operationFail);
            throw new CardException("Incorrect card data:" + request.getCardFromNumber(), operationFail, HttpStatus.NOT_FOUND);
        }
    }

    //перевод резервированного баланса в случае успеха
    public Operation confirmOperation(Confirm confirm) {
        var id = confirm.getOperationId();
        Operation transferOperation = operationsRepository.getOperationById(confirm.getOperationId());
        var confirmOperation = new Operation(OperationTypes.CONFIRM_TRANSFER);
        if (!operationsRepository.containOperation(id)) {
            confirmOperation.setOperationStatus(OperationStatuses.FAIL);
            operationsRepository.addOperation(confirmOperation);
            throw new OperationException("Operation not found.", confirmOperation, HttpStatus.NOT_FOUND);
        } else if (!operationsRepository.isNotConfirmedOperation(id)) {
            confirmOperation.setOperationStatus(OperationStatuses.FAIL);
            operationsRepository.addOperation(confirmOperation);
            throw new OperationException("Operation already confirmed.", confirmOperation, HttpStatus.NOT_FOUND);
        } else if (confirm.getCode().equals(CODE)) {
            TransferRequest request = transferOperation.getRequest();
            Card senderCard = cardsRepository.getCardById(request.getCardFromNumber());
            Card receiverCard = cardsRepository.getCardById(transferOperation.getRequest().getCardToNumber());
            synchronized (this) {
                try {
                    double spendAmount = senderCard.getReservedBalance().get(transferOperation.getOperationId());
                    senderCard.deleteReservedBalance(transferOperation.getOperationId());
                    senderCard.spendBalance(spendAmount);
                    receiverCard.addBalance(spendAmount);
                } catch (Exception e) {
                    transferOperation.setOperationStatus(OperationStatuses.FAIL);
                    confirmOperation.setOperationStatus(OperationStatuses.FAIL);
                    operationsRepository.addOperation(confirmOperation);
                    throw new OperationException("Operation failed", confirmOperation, HttpStatus.INTERNAL_SERVER_ERROR);
                } finally {
                    senderCard.deleteReservedBalance(transferOperation.getOperationId());
                }
            }
            confirmOperation.setOperationStatus(OperationStatuses.SUCCESS);
            transferOperation.setOperationStatus(OperationStatuses.SUCCESS_CONFIRMED);
            operationsRepository.addOperation(confirmOperation);
            return confirmOperation;
        } else {
            confirmOperation.setOperationStatus(OperationStatuses.FAIL);
            throw new OperationException("Confirmation code incorrect", confirmOperation, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
