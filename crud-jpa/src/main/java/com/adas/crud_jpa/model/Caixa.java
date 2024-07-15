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

    @ManyToMany
    @JoinTable(name="caixa_produto",
            joinColumns = @JoinColumn(name="caixa_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    private List<Produto> produtos;
}
