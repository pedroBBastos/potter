package com.challenge.personagem;

import com.challenge.CrudService;
import com.challenge.dto.PersonagemCriacaoDTO;
import com.challenge.dto.PersonagemDTO;
import com.challenge.dto.PersonagemUpdateDTO;
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
        this.validateCriacaoTO(personagemCriacaoDTO);
        PersonagemEntity saved = this.save(modelMapper.map(personagemCriacaoDTO, PersonagemEntity.class));
        return modelMapper.map(saved, PersonagemDTO.class);
    }

    public PersonagemDTO atualizarPersonagem(PersonagemUpdateDTO personagemUpdateDTO) {
        this.validateUpdateTO(personagemUpdateDTO);
        PersonagemEntity updated = this.atualizarPersonagemByDTO(personagemUpdateDTO);
        return modelMapper.map(this.save(updated), PersonagemDTO.class);
    }

    private PersonagemEntity atualizarPersonagemByDTO(PersonagemUpdateDTO personagemUpdateDTO) {
        var personagemEntity = this.personagemRepository
                .findByNameAndHouse(personagemUpdateDTO.getName(), personagemUpdateDTO.getHouse());
        PersonagemEntity atualizado = modelMapper.map(personagemUpdateDTO, PersonagemEntity.class);
        atualizado.setId(personagemEntity.getId());
        atualizado.setDataCriacao(personagemEntity.getDataCriacao());
        return atualizado;
    }

    private void validateCriacaoTO(PersonagemCriacaoDTO personagemCriacaoDTO) {
        this.validateNullTO(personagemCriacaoDTO);
        this.validatePersonagemExistente(personagemCriacaoDTO);
    }

    private void validateUpdateTO(PersonagemUpdateDTO personagemCriacaoDTO) {
        this.validateNullTO(personagemCriacaoDTO);
        this.validatePersonagemInexistente(personagemCriacaoDTO);
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

    private void validatePersonagemInexistente(PersonagemUpdateDTO personagemUpdateDTO) {
        if(!this.personagemRepository.existsByNameAndHouse(personagemUpdateDTO.getName(),
                personagemUpdateDTO.getHouse())) {
            throw new PersonagemException("Personagem não encontrado!");
        }
    }
}
