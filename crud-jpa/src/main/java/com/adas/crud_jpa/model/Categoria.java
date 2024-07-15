package com.adas.crud_jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


//Define automaticamente o construturo vazio da classe
@NoArgsConstructor

//Define automaticamente o construtor com TODOS os atributos da classe
@AllArgsConstructor

//Define os getters e setters automaticamente
//Ou pode substituir pela anotação @Data
@Getter @Setter

//Gera todas as possibilidades de construtores que nao sao os vazios ou os totalmente cheios
@Builder

//Gera automaticamente uma tabela no banco de dados, tendo as colunas dessa tabela de forma
// espelhada com os atributos da classe categoria
@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NonNull
    private String nome;

    @NonNull
    private boolean status;

    @JsonIgnore
    @OneToMany(mappedBy = "categoria")
    private List<Produto> produtos;

}