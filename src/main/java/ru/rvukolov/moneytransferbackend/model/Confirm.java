package ru.rvukolov.moneytransferbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class Confirm {
    private final UUID operationId;
    private final String code;
}
