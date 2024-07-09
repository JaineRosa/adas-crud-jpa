package com.adas.crud_jpa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Caixa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    private boolean status;
    private Double saldo;
    private Double limite;

}
