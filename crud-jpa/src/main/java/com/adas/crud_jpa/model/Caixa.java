package com.adas.crud_jpa.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor

@AllArgsConstructor

@Data
@Builder

@Entity
public class Caixa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NonNull
    private boolean status;

    @NonNull
    private Double saldo;

    @NonNull
    private Double limite;

    /* Criando a tabela auxiliar "caixa_produto"npara receber o vinculo entre as tabelas 'caixa' e 'Produto'.
    *Vinculando a chave primaria de Caixa como chave estrangeira no campo 'caixa_id'
    * Vinculando a chave primaria de Produto  como chave estrangeira no campo 'produto_id'
    * */

    @ManyToMany
    @JoinTable(name="caixa_produto",
            joinColumns = @JoinColumn(name="caixa_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    private List<Produto> produtosCaixa;
}
