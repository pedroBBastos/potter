package com.challenge.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Classe para representação de um personagem de Harry Potter
 * @author PedroBastos
 */

@Entity
@Data
@Table(name = "personagem",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonagemEntity implements BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Nome não pode ser nulo!")
    @NotEmpty(message = "Nome não pode ser vazio!")
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "role")
    private String role;

    @Column(name = "school")
    private String school;

    @NotNull(message = "Casa não pode ser nula!")
    @NotEmpty(message = "Casa não pode ser vazia!")
    @Column(name = "house", nullable = false)
    private String house;

    @Column(name = "patronus")
    private String patronus;

    @NotNull(message = "Data criação não pode ser nula!")
    @Column(name = "data_criacao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @NotNull(message = "Data de edição não pode ser nula!")
    @Column(name = "data_edicao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEdicao;
}
