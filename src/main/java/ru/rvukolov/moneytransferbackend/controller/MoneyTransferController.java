package ru.rvukolov.moneytransferbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rvukolov.moneytransferbackend.exceptions.CardException;
import ru.rvukolov.moneytransferbackend.exceptions.UserAlreadyRegisteredException;
import ru.rvukolov.moneytransferbackend.model.*;
import ru.rvukolov.moneytransferbackend.model.out.FailResponse;
import ru.rvukolov.moneytransferbackend.model.out.Response;
import ru.rvukolov.moneytransferbackend.service.MoneyTransferService;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
public class MoneyTransferController {
    private MoneyTransferService moneyTransferService;

    public MoneyTransferController(MoneyTransferService moneyTransferService) {
        this.moneyTransferService = moneyTransferService;
    }
    @PostMapping("/transfer")
    public Operation transfer(@RequestBody TransferRequest request) {
        return moneyTransferService.transfer(request);
    }

    @PostMapping("/confirmOperation")
    public void confirmOperation() {

    }

    @ExceptionHandler(CardException.class)
    ResponseEntity<Response> handleCardNotFoundException(CardException e) {
        return ResponseEntity.status(400).body(new FailResponse(e, e.getOperation()));
    }


}
