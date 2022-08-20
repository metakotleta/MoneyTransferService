package ru.rvukolov.moneytransferbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rvukolov.moneytransferbackend.exceptions.CardNotFoundException;
import ru.rvukolov.moneytransferbackend.exceptions.UserAlreadyRegisteredException;
import ru.rvukolov.moneytransferbackend.model.Amount;
import ru.rvukolov.moneytransferbackend.model.Card;
import ru.rvukolov.moneytransferbackend.model.out.FailResponse;
import ru.rvukolov.moneytransferbackend.model.out.Response;
import ru.rvukolov.moneytransferbackend.service.CardsService;
import ru.rvukolov.moneytransferbackend.service.MoneyTransferService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/cards")
public class CardsController {

    private CardsService cardsService;

    public CardsController(CardsService cardsService) {
        this.cardsService = cardsService;
    }

    @GetMapping("/{cardId}")
    public Response getCardById(@PathVariable String cardId) {
        var operation = cardsService.getCardById(cardId);
        return new Response(operation);
    }

    @PostMapping("/register")
    public Response addCard(@RequestBody Card card, HttpServletResponse response) {
        var operation = cardsService.addCard(card);
        return new Response(operation);
    }

    @PostMapping("/{cardId}/topUp")
    public Response topUpBalance(@PathVariable String cardId, @RequestBody Amount amount) {
        var operation = cardsService.addBalance(cardId, amount);
        return new Response(operation);
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    ResponseEntity<Response> handleUserAlreadyRegisteredException(UserAlreadyRegisteredException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new FailResponse(e, e.getOperation()));
    }

}
