package com.challenge.personagem;

import com.challenge.client.PotterAPIHousesClient;
import com.challenge.dto.*;
import com.challenge.entity.PersonagemEntity;
import com.challenge.exception.CasaException;
import com.challenge.exception.ParametroInvalidoException;
import com.challenge.exception.PersonagemException;
import com.challenge.repository.PersonagemRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersonagemService {

    private final ModelMapper modelMapper;
    private final PersonagemRepository personagemRepository;
    private final PotterAPIHousesClient potterAPIHousesClient;
    private final ValidatorFactory validatorFactory;


    public PersonagemDTO criarNovoPersonagem(PersonagemCriacaoDTO personagemCriacaoDTO) {
        PersonagemEntity personagemEntity = this.validateCriacaoTO(personagemCriacaoDTO);
        return modelMapper.map(this.personagemRepository.save(personagemEntity), PersonagemDTO.class);
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
        if(house == null) {
            return this.personagemRepository.findAll();
        }
        return  this.personagemRepository.findAllByHouse(house);
    }

    private PersonagemEntity atualizarPersonagemEntityByDTO(PersonagemEntity personagemEntity,
                                                            PersonagemUpdateDTO personagemUpdateDTO) {
        PersonagemEntity atualizado = modelMapper.map(personagemUpdateDTO, PersonagemEntity.class);
        atualizado.setId(personagemEntity.getId());
        atualizado.setDataCriacao(personagemEntity.getDataCriacao());
        return atualizado;
    }

    private PersonagemEntity validateCriacaoTO(PersonagemCriacaoDTO personagemCriacaoDTO) {
        this.validateNullTO(personagemCriacaoDTO);
        this.validatePersonagemExistente(personagemCriacaoDTO);

        var personagemEntity = this.novoPersonagemByDTO(personagemCriacaoDTO);
        this.validateColunasInvalidas(personagemEntity);

        this.validateCasaExistente(personagemEntity.getHouse());
        return personagemEntity;
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

    private PersonagemEntity novoPersonagemByDTO(PersonagemCriacaoDTO personagemCriacaoDTO) {
        return modelMapper.map(personagemCriacaoDTO, PersonagemEntity.class);
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

    private void validateColunasInvalidas(PersonagemEntity personagemEntity) {
        Set<ConstraintViolation<PersonagemEntity>> validate =
                this.validatorFactory.getValidator().validate(personagemEntity);
        if(!validate.isEmpty()) {
            throw new ParametroInvalidoException(new ArrayList<>(validate).get(0).getMessage());
        }
    }
}
