package ru.rvukolov.moneytransferbackend.repository;

import org.springframework.stereotype.Repository;
import ru.rvukolov.moneytransferbackend.exceptions.CardNotFoundException;
import ru.rvukolov.moneytransferbackend.exceptions.UserAlreadyRegisteredException;
import ru.rvukolov.moneytransferbackend.model.*;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CardsRepository {
    private Map<String, Card> cards = new ConcurrentHashMap<>();

    public Operation addCard(Card card){
        Operation operation;
        if (!cards.containsKey(card.getCardId())) {
            cards.put(card.getCardId(), card);
            operation = new Operation(OperationTypes.ADD_CARD, card).setOperationStatus(OperationStatuses.SUCCESS);
        } else {
            operation = new Operation(OperationTypes.ADD_CARD).setOperationStatus(OperationStatuses.FAIL);
            throw new UserAlreadyRegisteredException("User already Registered", operation);
        }
        return operation;
    }

    public Operation addBalance(String cardId, double amount) {
        Card card = cards.get(cardId);
        card.addBalance(amount);
        Operation operation = new Operation(OperationTypes.ADD_BALANCE, card).setOperationStatus(OperationStatuses.SUCCESS);
        return operation;
    }

    public Operation getCardById(String cardId) {
        Card card = cards.get(cardId);
        Operation operation = new Operation(OperationTypes.GET_CARD, card).setOperationStatus(OperationStatuses.SUCCESS);
        return operation;
    }

    public Operation transfer(TransferRequest request) {
        Operation operation = new Operation(OperationTypes.TRANSFER);
        Card senderCard = checkCardExists(request.getCardFromNumber(), operation);
        Card receiverCard = checkCardExists(request.getCardToNumber(), operation);
        double amount = request.getAmount().getValue();
        if (checkSenderCard(senderCard,request)) {
            senderCard.spendBalance(amount);
            receiverCard.addBalance(amount);
        } else {

        }
        return operation.setOperationStatus(OperationStatuses.SUCCESS);
    }

    private Card checkCardExists(String cardId, Operation operation) {
        if (cards.containsKey(cardId)) {
            return cards.get(cardId);
        } else {
            throw new CardNotFoundException("Card was not found", cardId, operation.setOperationStatus(OperationStatuses.FAIL));
        }
    }

    private boolean checkSenderCard(Card card, TransferRequest request) {
        return Objects.equals(card.getCardId(), request.getCardFromNumber()) && Objects.equals(card.getValidTill(), request.getCardFromValidTill())
                    && Objects.equals(card.getCardCVV(), request.getCardFromCVV());
    }
}
