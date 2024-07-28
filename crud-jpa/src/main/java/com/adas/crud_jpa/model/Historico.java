package com.adas.crud_jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

    private LocalDateTime dataTransacao;

    //Campo que irá receber a chave orimaria da tabela 'cliente' vomo chave estrangeira da tabela 'historico'

    @ManyToOne
    @JoinColumn(name="cliente_id")
    private Cliente cliente;


    @ManyToMany
    @JoinTable(
            name = "registro_venda",
            joinColumns = @JoinColumn(name = "historico_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    private List<Produto> produtosHistoricos;


}
