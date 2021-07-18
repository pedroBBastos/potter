package com.challenge;

import com.challenge.entity.BaseEntity;
import com.challenge.exception.ParametroInvalidoException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Classe abstrata para representar um CrudService, com operações de CRUD padrão
 *
 * @param <T extends BaseEntity>
 * @author PedroBastos
 */
public abstract class CrudService<T extends BaseEntity> {

    private final JpaRepository<T, Long> jpaRepository;

    protected CrudService(JpaRepository<T, Long> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public List<T> findAll() {
        return this.jpaRepository.findAll();
    }

    public T findById(Long id) {
        if(id == null)
            throw new ParametroInvalidoException("Id fornecido não pode ser nulo");
        return this.jpaRepository.findById(id).orElse(null);
    }

    public T save(T baseEntity) {
        if(baseEntity == null)
            throw new ParametroInvalidoException("Entidade fornecida não pode ser nula");
        return this.jpaRepository.save(baseEntity);
    }

    public void delete(T baseEntity) {
        if(baseEntity == null)
            throw new ParametroInvalidoException("Entidade fornecida não pode ser nula");
        this.jpaRepository.delete(baseEntity);
    }

    public void deleteById(Long id) {
        if(id == null)
            throw new ParametroInvalidoException("Id fornecido não pode ser nulo");
        this.jpaRepository.deleteById(id);
    }

}
