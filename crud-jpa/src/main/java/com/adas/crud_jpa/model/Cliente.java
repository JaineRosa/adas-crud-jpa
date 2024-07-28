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


    private String nome;
    private String cpf;
    private String email;

//    @OneToMany(mappedBy = "cliente")
//    private List<Historico> historicos;

}
