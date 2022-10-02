package ru.rvukolov.moneytransferbackend.exceptions;

import org.springframework.http.HttpStatus;
import ru.rvukolov.moneytransferbackend.model.Operation;
import ru.rvukolov.moneytransferbackend.model.out.Response;

public abstract class AApplicationException extends RuntimeException {
    private Response response;
    private HttpStatus status;

    public AApplicationException(String message, Operation operation, HttpStatus status) {
        super(message);
        response = new Response(operation).setMessage(message);
        this.status = status;
    }

    public AApplicationException(String message, Operation operation, HttpStatus status, Throwable e) {
        super(message, e);
        response = new Response(operation).setMessage(message);
        this.status = status;
    }

    public Response getResponse() {
        return response;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
