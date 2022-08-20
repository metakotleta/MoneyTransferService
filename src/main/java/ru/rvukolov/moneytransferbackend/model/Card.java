package ru.rvukolov.moneytransferbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Setter;

public class Card {
    private String cardId;
    private String validTill;
    private String cardCVV;
    private String name;
    private String surname;
    private double balance;

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

}
