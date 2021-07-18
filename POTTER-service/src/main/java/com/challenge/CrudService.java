package com.challenge;

import com.challenge.entity.BaseEntity;
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
        return this.jpaRepository.findById(id).orElse(null);
    }

    public T save(T baseEntity) {
        return this.jpaRepository.save(baseEntity);
    }

    public void delete(T baseEntity) {
        this.jpaRepository.delete(baseEntity);
    }

    public void deleteById(Long id) {
        this.jpaRepository.deleteById(id);
    }

}
