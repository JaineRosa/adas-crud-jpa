package com.adas.crud_jpa.service;

import com.adas.crud_jpa.model.Historico;
import com.adas.crud_jpa.repository.HistoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricoService {

    @Autowired
    private HistoricoRepository historicoRepository;

    public List<Historico> buscarTodos() {
        return historicoRepository.findAll();
    }

    public Historico buscarPorId(int id) {
        return historicoRepository.findById(id).orElse(null);
    }

    public Historico salvar(Historico historico) {
        return historicoRepository.save(historico);
    }

    public void excluir(Historico historico) {
        historicoRepository.delete(historico);
    }
}