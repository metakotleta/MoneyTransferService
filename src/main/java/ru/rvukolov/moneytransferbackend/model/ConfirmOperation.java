package ru.rvukolov.moneytransferbackend.model;

import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true)
public class ConfirmOperation extends Operation{
    private Operation confirmedOperation;

    public ConfirmOperation(OperationTypes operationType) {
        super(operationType);
    }
}
