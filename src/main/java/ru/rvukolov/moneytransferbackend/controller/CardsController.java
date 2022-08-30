package ru.rvukolov.moneytransferbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.rvukolov.moneytransferbackend.exceptions.AApplicationException;
import ru.rvukolov.moneytransferbackend.exceptions.UserAlreadyRegisteredException;
import ru.rvukolov.moneytransferbackend.model.*;
import ru.rvukolov.moneytransferbackend.model.out.CardDto;
import ru.rvukolov.moneytransferbackend.model.out.Response;
import ru.rvukolov.moneytransferbackend.model.out.ValidationError;
import ru.rvukolov.moneytransferbackend.service.CardsService;
import ru.rvukolov.moneytransferbackend.service.OperationsService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cards")
public class CardsController {

    private Logger log = LoggerFactory.getLogger(CardsController.class);
    private CardsService cardsService;
    private OperationsService operationsService;

    public CardsController(CardsService cardsService, OperationsService operationsService) {
        this.cardsService = cardsService;
        this.operationsService = operationsService;
    }
    //TODO: Получение списка всех карт

    @GetMapping("/{cardId}")
    public Response getCardById(@PathVariable String cardId) {
        log.debug("Getting card by id, requsted card: {}", cardId);
        var operation = cardsService.getCardById(cardId);
        var cardDto = new CardDto(operation.getCard());
        return new Response(operation).setCardDto(cardDto);
    }

    @PostMapping("/register")
    public Response addCard(@RequestBody @Valid Card card) throws UserAlreadyRegisteredException {
        var operation = cardsService.addCard(card);
        return new Response(operation).setCardId(card.getCardId());
    }

    @PostMapping("/{cardId}/topUp")
    public Response topUpBalance(@PathVariable String cardId, @RequestBody @Valid Amount amount) {
        var operation = cardsService.addBalance(cardId, amount);
        var cardDto = new CardDto(operation.getCard().getCardId());
        return new Response(operation).setCardDto(cardDto);
    }

    @ExceptionHandler(AApplicationException.class)
    Response handleUserAlreadyRegisteredException(AApplicationException e, HttpServletResponse response) {
        response.setStatus(e.getStatus().value());
        return e.getResponse();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    Response handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletResponse response) {
        var operation = new Operation(OperationTypes.ERROR);
        operationsService.addOperation(operation);
        var error = new ValidationError()
                .setErrorMethodNames(e.getParameter().getMethod().getAnnotation(PostMapping.class).value())
                .setInvalidFields(e.getBindingResult().getFieldErrors().stream().map(er -> er.getField()).collect(Collectors.toList()));
        return new Response(operation).setValidationError(error);
    }
}
