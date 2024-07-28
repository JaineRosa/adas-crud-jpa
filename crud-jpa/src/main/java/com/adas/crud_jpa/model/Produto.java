package com.adas.crud_jpa.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor

@AllArgsConstructor

@Getter @Setter

@Builder

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NonNull
    private String nome;

    @NonNull
    private Double preco;

    @NonNull
    private Integer quantidade;

    //Vinculando varios registros da tabela Produto com um único registro da tabela Categoria.
    //A tabela Produto recebe a chave primaria de categoria como chave estrangeira dentro do campo categoria_id.
    @ManyToOne
    @JoinColumn(name="categoria_id")
    private Categoria categoria;

    @ManyToMany(mappedBy = "produtosCaixa")
    private List<Caixa> caixas;

    @ManyToMany
    @JoinTable(
            name="produto_historico",
            joinColumns = @JoinColumn(name="produto_id"),
            inverseJoinColumns = @JoinColumn(name="historico_id")
    )
    private List<Historico>historicos;
}