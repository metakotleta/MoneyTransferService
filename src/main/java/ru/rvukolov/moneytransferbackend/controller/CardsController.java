package ru.rvukolov.moneytransferbackend.controller;

import org.springframework.web.bind.annotation.*;
import ru.rvukolov.moneytransferbackend.exceptions.AApplicationException;
import ru.rvukolov.moneytransferbackend.exceptions.UserAlreadyRegisteredException;
import ru.rvukolov.moneytransferbackend.model.Amount;
import ru.rvukolov.moneytransferbackend.model.Card;
import ru.rvukolov.moneytransferbackend.model.out.CardDto;
import ru.rvukolov.moneytransferbackend.model.out.Response;
import ru.rvukolov.moneytransferbackend.service.CardsService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/cards")
public class CardsController {

    private CardsService cardsService;

    public CardsController(CardsService cardsService) {
        this.cardsService = cardsService;
    }
    //TODO: Получение списка всех карт

    @GetMapping("/{cardId}")
    public Response getCardById(@PathVariable String cardId) {
        var operation = cardsService.getCardById(cardId);
        var cardDto = new CardDto(operation.getCard());
        return new Response(operation).setCardDto(cardDto);
    }

    @PostMapping("/register")
    public Response addCard(@RequestBody Card card) throws UserAlreadyRegisteredException {
        var operation = cardsService.addCard(card);
        return new Response(operation).setCardId(card.getCardId());
    }

    @PostMapping("/{cardId}/topUp")
    public Response topUpBalance(@PathVariable String cardId, @RequestBody Amount amount) {
        var operation = cardsService.addBalance(cardId, amount);
        var cardDto = new CardDto(operation.getCard().getCardId());
        return new Response(operation).setCardDto(cardDto);
    }

    @ExceptionHandler(AApplicationException.class)
    Response handleUserAlreadyRegisteredException(AApplicationException e, HttpServletResponse response) {
        response.setStatus(e.getStatus().value());
        return e.getResponse();
    }
}
