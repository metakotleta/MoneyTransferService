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
    private String cardId;
    private String validTill;
    private String name;
    private String surname;

    public CardDto(String cardId) {
        this.cardId = cardId;
    }

    public CardDto(Card card) {
        this.cardId = card.getCardId();
        this.validTill = card.getValidTill();
        this.name = card.getName();
        this.surname = card.getSurname();
    }
}
