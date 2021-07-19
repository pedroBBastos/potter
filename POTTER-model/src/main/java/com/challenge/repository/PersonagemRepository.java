package com.challenge.repository;

import com.challenge.entity.PersonagemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para manipulação de dados da entidade PersonagemEntity
 * @author PedroBastos
 */

@Repository
public interface PersonagemRepository extends JpaRepository<PersonagemEntity, Long> {

    boolean existsByNameAndHouse(String name, String house);
    PersonagemEntity findByNameAndHouse(String name, String house);
}
