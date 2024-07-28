package com.adas.crud_jpa.repository;

import com.adas.crud_jpa.model.Historico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoricoRepository extends JpaRepository<Historico, Integer> {

    @Query("SELECT h.id AS historicoId, c.nome AS clienteNome, p.nome AS produtoNome " +
            "FROM Historico h " +
            "JOIN h.cliente c " +
            "JOIN h.produtosHistoricos p " +
            "ORDER BY h.id ASC")
    List<Object[]> findHistoricoClienteProduto();



}