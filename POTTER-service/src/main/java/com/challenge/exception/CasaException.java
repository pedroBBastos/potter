package com.challenge.exception;

/**
 * Exceção criada para indicar que a casa informada no payload não existe.
 * @author PedroBastos
 */
public class CasaException extends RuntimeException {

    public CasaException(String message) {
        super(message);
    }
}
