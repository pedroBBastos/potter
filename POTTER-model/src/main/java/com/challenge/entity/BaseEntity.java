package com.challenge.entity;

import java.io.Serializable;

/**
 * Interface base para todas as entidades do projeto, incluindo método para obrigatoriedade de id
 * @author PedroBastos
 */
public interface BaseEntity extends Serializable {
    Long getId();
}
