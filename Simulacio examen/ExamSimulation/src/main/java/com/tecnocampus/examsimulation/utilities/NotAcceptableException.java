package com.tecnocampus.examsimulation.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (value = HttpStatus.NOT_ACCEPTABLE, reason = "Invalid data")
public class NotAcceptableException extends RuntimeException {

    public NotAcceptableException() {
        super();
    }
    public NotAcceptableException(String message) {

        super(message);
    }
}
