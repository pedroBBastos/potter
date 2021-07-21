package com.challenge.exception;

/**
 * Exceção criada para indicar erros provenientes de parâmetros inadequados no payload de requisições.
 * @author PedroBastos
 */
public class ParametroInvalidoException extends RuntimeException {

    public ParametroInvalidoException(String message) {
        super(message);
    }
}
