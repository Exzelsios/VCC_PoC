package de.novatec.vccadmin.kubernetes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidOperationException extends Exception {

    public InvalidOperationException(String message) {
        super(message);
    }
}
