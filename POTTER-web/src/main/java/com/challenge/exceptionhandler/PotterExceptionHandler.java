package com.challenge.exceptionhandler;

import com.challenge.exception.ParametroInvalidoException;
import com.challenge.exception.PersonagemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class PotterExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PersonagemException.class)
    public final ResponseEntity<ErrorMessage> handlePersonagemException(PersonagemException ex,
                                                                        ServletWebRequest request) {
        return new ResponseEntity<>(ErrorMessage.builder().message(ex.getMessage())
                .details(request.getDescription(false)).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ParametroInvalidoException.class)
    public final ResponseEntity<ErrorMessage> handleParametroInvalidoException(ParametroInvalidoException ex,
                                                                               ServletWebRequest request) {
        return new ResponseEntity<>(ErrorMessage.builder().message(ex.getMessage())
                .details(request.getDescription(false)).build(), HttpStatus.BAD_REQUEST);
    }

}
