package com.ec.pichincha.registro.bancario.model.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {
    private int status;
    private String message;
    private List<String> details;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.details = new ArrayList<>();
    }

    public ErrorResponse(int status, String message, List<String> details) {
        this.status = status;
        this.message = message;
        this.details = details;
    }

}
