package com.adas.crud_jpa.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor

@AllArgsConstructor
@Data
@Builder

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NonNull
    private String nome;

    @NonNull
    private String email;

    @OneToMany(mappedBy = "cliente")
    private List<Historico> historicos;

}
