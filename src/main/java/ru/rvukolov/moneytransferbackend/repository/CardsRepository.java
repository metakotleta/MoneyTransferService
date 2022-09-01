package ru.rvukolov.moneytransferbackend.repository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import ru.rvukolov.moneytransferbackend.exceptions.CardException;
import ru.rvukolov.moneytransferbackend.exceptions.UserAlreadyRegisteredException;
import ru.rvukolov.moneytransferbackend.model.*;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CardsRepository {
    private Map<String, Card> cards = new ConcurrentHashMap<>();

    public Map<String, Card> getCards() {
        return cards;
    }

    public void addCard(Card card){
        cards.put(card.getCardId(), card);
    }

    public Card getCardById(String cardId) {
        return cards.get(cardId);
    }

    public boolean hasCard(String cardId) {
        return cards.containsKey(cardId);
    }

    public void addBalance(String cardId, double amount) {
        cards.get(cardId).addBalance(amount);
    }

    public Card checkCardExists(String cardId, Operation operation) {
        //TODO: вынести исключение в сервис
        if (cards.containsKey(cardId)) {
            return cards.get(cardId);
        } else {
            throw new CardException("Card was not found: " + cardId, operation.setOperationStatus(OperationStatuses.FAIL), HttpStatus.NOT_FOUND);
        }
    }

    public boolean checkSenderCard(Card card, TransferRequest request) {
        return Objects.equals(card.getCardId(), request.getCardFromNumber()) && Objects.equals(card.getValidTill(), request.getCardFromValidTill())
                    && Objects.equals(card.getCardCVV(), request.getCardFromCVV());
    }
}
