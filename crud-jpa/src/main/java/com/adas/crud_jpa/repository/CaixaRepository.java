package com.adas.crud_jpa.repository;

import com.adas.crud_jpa.model.Caixa;
import com.adas.crud_jpa.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaixaRepository extends JpaRepository<Caixa, Integer> {
}
