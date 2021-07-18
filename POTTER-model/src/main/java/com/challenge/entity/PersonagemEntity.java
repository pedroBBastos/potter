package com.challenge.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Classe para representação de um personagem de Harry Potter
 * @author PedroBastos
 */

@Entity
@Data
@Table(name = "personagem")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonagemEntity implements BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "role")
    private String role;

    @Column(name = "school")
    private String school;

    @Column(name = "house")
    private String house;

    @Column(name = "patronus")
    private String patronus;
}
