package com.challenge.personagem;

import com.challenge.CrudService;
import com.challenge.dto.PersonagemCriacaoDTO;
import com.challenge.dto.PersonagemDTO;
import com.challenge.entity.PersonagemEntity;
import com.challenge.exception.ParametroInvalidoException;
import com.challenge.exception.PersonagemException;
import com.challenge.repository.PersonagemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PersonagemService extends CrudService<PersonagemEntity> {

    private ModelMapper modelMapper;
    private PersonagemRepository personagemRepository;

    public PersonagemService(PersonagemRepository personagemRepository,
                             ModelMapper modelMapper) {
        super(personagemRepository);
        this.personagemRepository = personagemRepository;
        this.modelMapper = modelMapper;
    }

    public PersonagemDTO criarNovoPersonagem(PersonagemCriacaoDTO personagemCriacaoDTO) {
        validateCriacaoTO(personagemCriacaoDTO);
        PersonagemEntity saved = this.save(modelMapper.map(personagemCriacaoDTO, PersonagemEntity.class));
        return modelMapper.map(saved, PersonagemDTO.class);
    }

    private void validateCriacaoTO(PersonagemCriacaoDTO personagemCriacaoDTO) {
        this.validateNullTO(personagemCriacaoDTO);
        this.validatePersonagemExistente(personagemCriacaoDTO);
    }

    private void validateNullTO(PersonagemDTO personagemDTO) {
        if(personagemDTO == null) {
            throw new ParametroInvalidoException("Objeto nulo!");
        }
    }

    private void validatePersonagemExistente(PersonagemCriacaoDTO personagemCriacaoDTO) {
        if(this.personagemRepository.existsByNameAndHouse(personagemCriacaoDTO.getName(),
                personagemCriacaoDTO.getHouse())) {
            throw new PersonagemException("Personagem já existente!");
        }
    }
}
