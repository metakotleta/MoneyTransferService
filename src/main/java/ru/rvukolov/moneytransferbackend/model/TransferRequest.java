package ru.rvukolov.moneytransferbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.rvukolov.moneytransferbackend.model.Amount;

@Getter
@AllArgsConstructor
public class TransferRequest {
    private String cardFromNumber;
    private String cardFromValidTill;
    private String cardFromCVV;
    private String cardToNumber;
    private Amount amount;
}
