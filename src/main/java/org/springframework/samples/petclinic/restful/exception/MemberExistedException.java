package org.springframework.samples.petclinic.restful.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MemberExistedException extends RuntimeException {
    public MemberExistedException(String message) {
        super(message);

    }
}
