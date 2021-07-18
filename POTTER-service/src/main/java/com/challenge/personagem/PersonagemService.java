package com.challenge.personagem;

import com.challenge.CrudService;
import com.challenge.dto.PersonagemCriacaoDTO;
import com.challenge.dto.PersonagemDTO;
import com.challenge.entity.PersonagemEntity;
import com.challenge.repository.PersonagemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PersonagemService extends CrudService<PersonagemEntity> {

    private ModelMapper modelMapper;

    public PersonagemService(PersonagemRepository personagemRepository,
                             ModelMapper modelMapper) {
        super(personagemRepository);
        this.modelMapper = modelMapper;
    }

    public PersonagemDTO criarNovoPersonagem(PersonagemCriacaoDTO personagemCriacaoDTO) {
        PersonagemEntity saved = this.save(modelMapper.map(personagemCriacaoDTO, PersonagemEntity.class));
        return modelMapper.map(saved, PersonagemDTO.class);
    }
}
