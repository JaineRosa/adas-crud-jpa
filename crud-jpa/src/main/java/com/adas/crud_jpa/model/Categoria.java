package com.adas.crud_jpa.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


//Define automaticamente o construtor vazio
@NoArgsConstructor
//defie automaticamento o construtor com todos os atributos.
@AllArgsConstructor
//Define automaticamento os metodos get e set para todos os atributos
@Getter
@Setter
//Gera todas as possibilidades de construtores que não são o vazio e o cheio.
@Builder

//Gera automaticamente uma tabel no banco de dados, tendo as colunas dessa tabela.
// de forma espelhada com os atributos da classe Categoria.
@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    @Nonnull
    private String nome;

    private boolean status;

}
