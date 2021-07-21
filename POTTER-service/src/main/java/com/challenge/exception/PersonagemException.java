package com.challenge.exception;

/**
 * Exceção criada para indicar problemas de CRUD de personagem durante o consumo da API.
 * @author PedroBastos
 */
public class PersonagemException extends RuntimeException {

    public PersonagemException(String message) {
        super(message);
    }
}
