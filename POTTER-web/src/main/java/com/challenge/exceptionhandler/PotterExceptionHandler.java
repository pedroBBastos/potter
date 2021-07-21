package com.challenge.exceptionhandler;

import com.challenge.exception.CasaException;
import com.challenge.exception.ParametroInvalidoException;
import com.challenge.exception.PersonagemException;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Classe com o objetivo de tratar todas as exceções lançadas pela aplicação.
 * Cada método trata uma exceção específica, devolvendo resposta com status HTTP especificado.
 * 
 * @author PedroBastos
 */

@ControllerAdvice
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

    @ExceptionHandler(CasaException.class)
    public final ResponseEntity<ErrorMessage> handleCasaException(CasaException ex,
                                                                  ServletWebRequest request) {
        return new ResponseEntity<>(ErrorMessage.builder().message(ex.getMessage())
                .details(request.getDescription(false)).build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {FeignException.class})
    public final ResponseEntity<ErrorMessage> handleFeignException(FeignException ex,
                                                                   ServletWebRequest request) {
        return new ResponseEntity<>(ErrorMessage.builder().message("Não foi possível completar a requisição. Contate o administrador" +
                " ou tente novamente mais tarde")
                .details(request.getDescription(false))
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
