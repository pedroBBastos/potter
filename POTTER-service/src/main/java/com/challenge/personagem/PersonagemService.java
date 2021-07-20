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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        this.save(modelMapper.map(personagemCriacaoDTO, PersonagemEntity.class));
        return personagemCriacaoDTO;
    }

    public PersonagemDTO atualizarPersonagem(PersonagemUpdateDTO personagemUpdateDTO) {
        PersonagemEntity personagem = this.validateModificacao(personagemUpdateDTO);
        personagem = this.atualizarPersonagemEntityByDTO(personagem, personagemUpdateDTO);
        return modelMapper.map(this.save(personagem), PersonagemDTO.class);
    }

    public void deletePersonagem(PersonagemDTO personagemDTO) {
        this.delete(this.validateModificacao(personagemDTO));
    }

    public List<PersonagemDTO> findAllPersonagemDTO(String house) {
        return this.findPersonagemEntities(house).stream()
                .map(personagemEntity ->
                        modelMapper.map(personagemEntity, PersonagemDTO.class))
                .collect(Collectors.toList());
    }

    private List<PersonagemEntity> findPersonagemEntities(String house) {
        List<PersonagemEntity> personagemEntities;
        if(house == null) {
            personagemEntities = this.findAll();
        } else {
            personagemEntities = this.personagemRepository.findAllByHouse(house);
        }
        return personagemEntities;
    }

    private PersonagemEntity atualizarPersonagemEntityByDTO(PersonagemEntity personagemEntity,
                                                            PersonagemUpdateDTO personagemUpdateDTO) {
        PersonagemEntity atualizado = modelMapper.map(personagemUpdateDTO, PersonagemEntity.class);
        atualizado.setId(personagemEntity.getId());
        atualizado.setDataCriacao(personagemEntity.getDataCriacao());
        return atualizado;
    }

    private void validateCriacaoTO(PersonagemCriacaoDTO personagemCriacaoDTO) {
        this.validateNullTO(personagemCriacaoDTO);
        this.validatePersonagemExistente(personagemCriacaoDTO);
    }

    private void validatePersonagemExistente(PersonagemCriacaoDTO personagemCriacaoDTO) {
        if(this.personagemRepository.existsByName(personagemCriacaoDTO.getName())) {
            throw new PersonagemException("Personagem já existente!");
        }
    }

    private PersonagemEntity validateModificacao(PersonagemDTO personagemDTO) {
        this.validateNullTO(personagemDTO);

        var personagemEntity = this.personagemRepository.findByName(personagemDTO.getName());
        if(personagemEntity == null)
            throw new PersonagemException("Personagem não encontrado!");

        return personagemEntity;
    }

    private void validateNullTO(PersonagemDTO personagemDTO) {
        if(personagemDTO == null) {
            throw new ParametroInvalidoException("Objeto nulo!");
        }
    }
}
