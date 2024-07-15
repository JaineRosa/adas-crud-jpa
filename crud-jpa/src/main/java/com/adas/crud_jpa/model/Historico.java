package com.adas.crud_jpa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor

@AllArgsConstructor
@Data

@Builder

@Entity
public class Historico {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="cliente_id")
    private Cliente cliente;


    @ManyToMany(mappedBy = "historicos")
    private List<Produto> produtos;
}
