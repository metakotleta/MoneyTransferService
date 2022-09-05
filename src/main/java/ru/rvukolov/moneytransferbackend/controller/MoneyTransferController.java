package ru.rvukolov.moneytransferbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.rvukolov.moneytransferbackend.exceptions.AApplicationException;
import ru.rvukolov.moneytransferbackend.model.Confirm;
import ru.rvukolov.moneytransferbackend.model.Operation;
import ru.rvukolov.moneytransferbackend.model.TransferRequest;
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
    public Operation confirmOperation(@RequestBody Confirm confirm) {
        return moneyTransferService.confirmOperation(confirm);
    }

    @ExceptionHandler(AApplicationException.class)
    Response handleCardException(AApplicationException e, HttpServletResponse response) {
        response.setStatus(e.getStatus().value());
        return e.getResponse();
    }
}
