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

    @ManyToOne
    @JoinColumn(name="categoria_id")
    private Categoria categoria;

    @ManyToMany(mappedBy = "produtos")
    private List<Caixa> caixas;

    @ManyToMany
    @JoinTable(
            name="produto_historico",
            joinColumns = @JoinColumn(name="produto_id"),
            inverseJoinColumns = @JoinColumn(name="historico_id")
    )
    private List<Historico>historicos;
}