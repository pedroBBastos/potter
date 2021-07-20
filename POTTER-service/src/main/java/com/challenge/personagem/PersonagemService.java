package com.challenge.personagem;

import com.challenge.client.PotterAPIHousesClient;
import com.challenge.dto.*;
import com.challenge.entity.PersonagemEntity;
import com.challenge.exception.CasaException;
import com.challenge.exception.ParametroInvalidoException;
import com.challenge.exception.PersonagemException;
import com.challenge.repository.PersonagemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonagemService {

    private ModelMapper modelMapper;
    private PersonagemRepository personagemRepository;
    private PotterAPIHousesClient potterAPIHousesClient;

    public PersonagemService(PersonagemRepository personagemRepository,
                             ModelMapper modelMapper,
                             PotterAPIHousesClient potterAPIHousesClient) {
        this.personagemRepository = personagemRepository;
        this.modelMapper = modelMapper;
        this.potterAPIHousesClient = potterAPIHousesClient;
    }

    public PersonagemDTO criarNovoPersonagem(PersonagemCriacaoDTO personagemCriacaoDTO) {
        this.validateCriacaoTO(personagemCriacaoDTO);
        this.personagemRepository.save(modelMapper.map(personagemCriacaoDTO, PersonagemEntity.class));
        return personagemCriacaoDTO;
    }

    public PersonagemDTO atualizarPersonagem(PersonagemUpdateDTO personagemUpdateDTO) {
        PersonagemEntity personagem = this.validateModificacao(personagemUpdateDTO);
        personagem = this.atualizarPersonagemEntityByDTO(personagem, personagemUpdateDTO);
        return modelMapper.map(this.personagemRepository.save(personagem), PersonagemDTO.class);
    }

    public void deletePersonagem(PersonagemDTO personagemDTO) {
        this.personagemRepository.delete(this.validateModificacao(personagemDTO));
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
            personagemEntities = this.personagemRepository.findAll();
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
        this.validateCasaExistente(personagemCriacaoDTO.getHouse());
    }

    private void validatePersonagemExistente(PersonagemCriacaoDTO personagemCriacaoDTO) {
        if(this.personagemRepository.existsByName(personagemCriacaoDTO.getName())) {
            throw new PersonagemException("Personagem já existente!");
        }
    }

    private CasasDTO validateCasaExistente(String casaId) {
        return this.potterAPIHousesClient.getHouses().getHouses().stream()
                .filter(casasDTO -> casasDTO.getId().equals(casaId))
                .findFirst().orElseThrow(() -> new CasaException(String.format("Casa '%s' não encontrada!", casaId)));
    }

    private PersonagemEntity validateModificacao(PersonagemDTO personagemDTO) {
        this.validateNullTO(personagemDTO);

        var personagemEntity = this.personagemRepository.findByName(personagemDTO.getName());
        if(personagemEntity == null) {
            throw new PersonagemException("Personagem não encontrado!");
        }

        return personagemEntity;
    }

    private void validateNullTO(PersonagemDTO personagemDTO) {
        if(personagemDTO == null) {
            throw new ParametroInvalidoException("Objeto nulo!");
        }
    }
}
