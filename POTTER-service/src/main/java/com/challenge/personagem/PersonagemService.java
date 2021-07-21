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

/**
 * Service com implementação das regras de CRUD de personagens.
 * @author PedroBastos
 */

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersonagemService {

    private final ModelMapper modelMapper;
    private final PersonagemRepository personagemRepository;
    private final PotterAPIHousesClient potterAPIHousesClient;
    private final ValidatorFactory validatorFactory;


    /**
     * Método para criação de novo personagem a partir de personagemCriacaoDTO.
     *
     * @param personagemCriacaoDTO - DTO recebido na requisição
     * @return PersonagemDTO a partir da entidade persistida;
     */
    public PersonagemDTO criarNovoPersonagem(PersonagemCriacaoDTO personagemCriacaoDTO) {
        var personagemEntity = this.validateCriacaoTO(personagemCriacaoDTO);
        return modelMapper.map(this.personagemRepository.save(personagemEntity), PersonagemDTO.class);
    }

    /**
     * Método para atualizar personagem a partir de personagemCriacaoDTO.
     * Antes de atualização ser efetuada, diversas validações de modificação são realizadas.
     * Aqui é considerado que um personagem pode trocar de casa, já que a
     * chave da entidade de personagem é o nome.
     *
     * @param personagemUpdateDTO - DTO recebido na requisição
     * @return PersonagemDTO a partir da entidade persistida;
     */
    public PersonagemDTO atualizarPersonagem(PersonagemUpdateDTO personagemUpdateDTO) {
        PersonagemEntity personagem = this.validateModificacao(personagemUpdateDTO);
        this.validateCasaExistente(personagemUpdateDTO.getHouse());

        personagem = this.atualizarPersonagemEntityByDTO(personagem, personagemUpdateDTO);
        return modelMapper.map(this.personagemRepository.save(personagem), PersonagemDTO.class);
    }

    /**
     * Método para deletar personagem a partir de personagemCriacaoDTO.
     * Antes de atualização ser efetuada, diversas validações de modificação são realizadas.
     *
     * @param personagemDTO - DTO recebido na requisição
     */
    public void deletePersonagem(PersonagemDTO personagemDTO) {
        this.personagemRepository.delete(this.validateModificacao(personagemDTO));
    }

    /**
     * Método para retornar personagens existentes, podendo ser realizado filtragem pela casa.
     *
     * @param house - casa a se filtrar na busca.
     * @return
     */
    public List<PersonagemDTO> findAll(String house) {
        return this.findPersonagemEntities(house).stream()
                .map(personagemEntity ->
                        modelMapper.map(personagemEntity, PersonagemDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Método para retornar personagem a partir de personagemDTO recebido da requisição.
     *
     * @param personagemDTO - DTO recebido na requisição
     * @return
     */
    public PersonagemDTO findByPersonagemDTO(PersonagemDTO personagemDTO) {
        this.validateNullTO(personagemDTO);
        this.validateNomeNull(personagemDTO.getName());
        return modelMapper.map(this.personagemRepository.findByName(personagemDTO.getName()), PersonagemDTO.class);
    }

    /**
     * Método para realizar busca de lista de personagens existentes, podendo ser filtrada por casa.
     *
     * @param house - parâmetro para filtragem de personagens por casa. Pode ser nulo.
     * @return - lista de personagens buscados
     */
    private List<PersonagemEntity> findPersonagemEntities(String house) {
        if(house == null) {
            return this.personagemRepository.findAll();
        }
        return  this.personagemRepository.findAllByHouse(house);
    }

    /**
     * Método para realizar a atualização de entidade existente em banco a partir
     * de dados recebidos por DTO da requisição.
     *
     * @param personagemEntity - entidade persisitida em banco a se atualizar
     * @param personagemUpdateDTO - DTO recebida da requisição com valores a se atualizar
     * @return - entidade atualizada
     */
    private PersonagemEntity atualizarPersonagemEntityByDTO(PersonagemEntity personagemEntity,
                                                            PersonagemUpdateDTO personagemUpdateDTO) {
        PersonagemEntity atualizado = modelMapper.map(personagemUpdateDTO, PersonagemEntity.class);
        atualizado.setId(personagemEntity.getId());
        atualizado.setDataCriacao(personagemEntity.getDataCriacao());
        return atualizado;
    }

    /**
     * Método para realizar validação de DTO recebido em requisição para criação de personagem.
     *
     * @param personagemCriacaoDTO - DTO recebido na requisição.
     * @return - entidade validade a ser persistida
     */
    private PersonagemEntity validateCriacaoTO(PersonagemCriacaoDTO personagemCriacaoDTO) {
        this.validateNullTO(personagemCriacaoDTO);
        this.validatePersonagemExistente(personagemCriacaoDTO);

        var personagemEntity = this.novoPersonagemByDTO(personagemCriacaoDTO);
        this.validateColunasInvalidas(personagemEntity);

        this.validateCasaExistente(personagemEntity.getHouse());
        return personagemEntity;
    }

    /**
     * Método para validar se personagem a se criar já existe na base de dados.
     * Verificação realizada a partir da chave única que é o nome do personagem.
     *
     * @param personagemCriacaoDTO - DTO recebido em requisição para criação de personagem
     * @throws PersonagemException caso personagem já exista.
     */
    private void validatePersonagemExistente(PersonagemCriacaoDTO personagemCriacaoDTO) {
        if(this.personagemRepository.existsByName(personagemCriacaoDTO.getName())) {
            throw new PersonagemException("Personagem já existente!");
        }
    }

    /**
     * Método que efetua a regra de validação do ID da casa passada na requisição de
     * criação ou atualização de personagem.
     *
     * @param casaId - id da casa passada como parâmetro
     * @return - CasasDTO existente e respectiva ao id passado
     * @throws CasaException caso id passado não tenha sido verificado
     */
    private CasasDTO validateCasaExistente(String casaId) {
        return this.potterAPIHousesClient.getHouses().getHouses().stream()
                .filter(casasDTO -> casasDTO.getId().equals(casaId))
                .findFirst().orElseThrow(() -> new CasaException(String.format("Casa '%s' não encontrada!", casaId)));
    }

    /**
     * Método para efetuar a transformação de um obejto PersonagemCriacaoDTO em um
     * objeto PersonagemEntity.
     *
     * @param personagemCriacaoDTO
     * @return - PersonagemEntity criado a partir de DTO recebido
     */
    private PersonagemEntity novoPersonagemByDTO(PersonagemCriacaoDTO personagemCriacaoDTO) {
        return modelMapper.map(personagemCriacaoDTO, PersonagemEntity.class);
    }

    /**
     * Método para realizar validação de DTO passado em requisição de modificação (atualização ou remoção de personagem).
     *
     * @param personagemDTO - DTO passado na requisição
     * @return - personagem a se modificar, respectivo ao DTO passado na requisição
     * @throws PersonagemException se personagem não for encontrado
     */
    private PersonagemEntity validateModificacao(PersonagemDTO personagemDTO) {
        this.validateNullTO(personagemDTO);
        this.validateNomeNull(personagemDTO.getName());

        var personagemEntity = this.personagemRepository.findByName(personagemDTO.getName());
        if(personagemEntity == null) {
            throw new PersonagemException("Personagem não encontrado!");
        }

        return personagemEntity;
    }

    /**
     * Método para realizar validação de PersonagemDTO nulo.
     *
     * @param personagemDTO
     * @throws ParametroInvalidoException caso DTO nulo.
     */
    private void validateNullTO(PersonagemDTO personagemDTO) {
        if(personagemDTO == null) {
            throw new ParametroInvalidoException("Objeto nulo!");
        }
    }

    /**
     * Método para realizar validação de chave nula (nome nulo).
     *
     * @param nome
     * @throws ParametroInvalidoException caso nome nulo.
     */
    private void validateNomeNull(String nome) {
        if(nome == null) {
            throw new ParametroInvalidoException("Chave 'name' não informada!");
        }
    }

    /**
     * Método para realizar validação de colunas de nova entidade a se persistir em banco.
     *
     * @param personagemEntity
     * @throws ParametroInvalidoException caso alguma coluna inválida.
     */
    private void validateColunasInvalidas(PersonagemEntity personagemEntity) {
        Set<ConstraintViolation<PersonagemEntity>> validate =
                this.validatorFactory.getValidator().validate(personagemEntity);
        if(!validate.isEmpty()) {
            throw new ParametroInvalidoException(new ArrayList<>(validate).get(0).getMessage());
        }
    }
}
