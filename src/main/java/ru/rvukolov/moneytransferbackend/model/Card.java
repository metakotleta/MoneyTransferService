package ru.rvukolov.moneytransferbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Card {
    @Size(min = 16)
    private String cardId;
    private String validTill;
    @Size(min = 3, max = 3)
    private String cardCVV;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    private double balance;
    private Map<UUID, Double> reservedBalance = new ConcurrentHashMap<>();

    public Card(String cardId, String validTill, String cardCVV, String name, String surname) {
        this.cardId = cardId;
        this.validTill = validTill;
        this.cardCVV = cardCVV;
        this.name = name;
        this.surname = surname;
    }


    public String getCardId() {
        return cardId;
    }

    public String getValidTill() {
        return validTill;
    }
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getCardCVV() {
        return cardCVV;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public double getBalance() {
        return balance;
    }

    public void addBalance(double balance) {
        this.balance += balance;
    }
    public void spendBalance(double balance) {
        this.balance -= balance;
    }

    public Map<UUID, Double> getReservedBalance() {
        return reservedBalance;
    }

    public void reserveSpendBalance(UUID operationId, double amount) {
        this.reservedBalance.put(operationId, amount);
    }

}
