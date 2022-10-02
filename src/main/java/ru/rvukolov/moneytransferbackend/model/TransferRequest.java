package ru.rvukolov.moneytransferbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.rvukolov.moneytransferbackend.model.Amount;

import javax.validation.Valid;

@Getter
@AllArgsConstructor
public class TransferRequest {
    private String cardFromNumber;
    private String cardFromValidTill;
    private String cardFromCVV;
    private String cardToNumber;
    @Valid
    private Amount amount;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getCardFromCVV() {
        return cardFromCVV;
    }
}
