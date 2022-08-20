package ru.rvukolov.moneytransferbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rvukolov.moneytransferbackend.model.Operation;
import ru.rvukolov.moneytransferbackend.service.OperationsService;

import java.util.List;

@RestController
@RequestMapping("/operations")
public class OperationsController {

    private OperationsService operationsService;

    public OperationsController(OperationsService operationsService) {
        this.operationsService = operationsService;
    }

    @GetMapping("/")
    public List<Operation> getOperations() {
        return operationsService.getOperations();
    }
}
