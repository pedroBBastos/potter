package com.challenge.personagem;

import com.challenge.CrudService;
import com.challenge.entity.PersonagemEntity;
import com.challenge.repository.PersonagemRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonagemService extends CrudService<PersonagemEntity> {
    public PersonagemService(PersonagemRepository personagemRepository) {
        super(personagemRepository);
    }
}
