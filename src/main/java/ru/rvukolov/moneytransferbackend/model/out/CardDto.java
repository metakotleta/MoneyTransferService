package ru.rvukolov.moneytransferbackend.model.out;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.rvukolov.moneytransferbackend.model.Card;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardDto {
    private final String cardId;
    private final String validTill;
    private final String name;
    private final String surname;
    private final double balance;

    public CardDto(Card card) {
        this.cardId = card.getCardId();
        this.validTill = card.getValidTill();
        this.name = card.getName();
        this.surname = card.getSurname();
        this.balance = card.getBalance();
    }
}
