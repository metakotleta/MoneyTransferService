package ru.rvukolov.moneytransferbackend.controller;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.rvukolov.moneytransferbackend.exceptions.AApplicationException;
import ru.rvukolov.moneytransferbackend.model.Confirm;
import ru.rvukolov.moneytransferbackend.model.Operation;
import ru.rvukolov.moneytransferbackend.model.OperationTypes;
import ru.rvukolov.moneytransferbackend.model.TransferRequest;
import ru.rvukolov.moneytransferbackend.model.out.Response;
import ru.rvukolov.moneytransferbackend.model.out.ValidationError;
import ru.rvukolov.moneytransferbackend.service.MoneyTransferService;
import ru.rvukolov.moneytransferbackend.service.OperationsService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class MoneyTransferController {
    private final MoneyTransferService moneyTransferService;
    private final OperationsService operationsService;

    public MoneyTransferController(MoneyTransferService moneyTransferService, OperationsService operationsService) {
        this.moneyTransferService = moneyTransferService;
        this.operationsService = operationsService;
    }

    @PostMapping("/transfer")
    public Operation transfer(@RequestBody @Valid TransferRequest request) {
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    Response handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletResponse response) {
        var operation = new Operation(OperationTypes.ERROR);
        operationsService.addOperation(operation);
        var error = new ValidationError()
                .setErrorMethodNames(e.getParameter().getMethod().getAnnotation(PostMapping.class).value())
                .setInvalidFields(e.getBindingResult().getFieldErrors().stream().map(er -> er.getField()).collect(Collectors.toList()));
        response.setStatus(500);
        return new Response(operation).setValidationError(error);
    }
}
