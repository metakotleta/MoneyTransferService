package ru.rvukolov.moneytransferbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rvukolov.moneytransferbackend.exceptions.CardNotFoundException;
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
        var oper = moneyTransferService.transfer(request);
        return oper;
    }

    @PostMapping("/confirmOperation")
    public void confirmOperation() {

    }

    @ExceptionHandler(CardNotFoundException.class)
    ResponseEntity<Response> handleCardNotFoundException(CardNotFoundException e) {
        return ResponseEntity.status(400).body(new FailResponse(e, e.getOperation()));
    }


}
