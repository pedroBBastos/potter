package com.challenge.repository;

import com.challenge.entity.PersonagemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório para manipulação de dados da entidade PersonagemEntity
 * @author PedroBastos
 */

@Repository
public interface PersonagemRepository extends JpaRepository<PersonagemEntity, Long> {

    boolean existsByName(String name);
    PersonagemEntity findByName(String name);
    List<PersonagemEntity> findAllByHouse(String house);
}
