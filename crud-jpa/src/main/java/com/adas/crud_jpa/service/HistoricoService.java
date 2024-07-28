package com.adas.crud_jpa.service;

import com.adas.crud_jpa.model.Cliente;
import com.adas.crud_jpa.model.Historico;
import com.adas.crud_jpa.model.Produto;
import com.adas.crud_jpa.repository.HistoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    //Declarando um objeto novo da classe Historico com base em um construtor
    //sob demanda, feito atraves da anotação "Builder"
    public void registrarVenda(Integer idCliente, List<Produto> produtos) {

        // Declarando um objeto da classe Cliente e passando valor para o atributo id
        Cliente cliente = Cliente
                .builder()
                .id(idCliente)
                .build();

        // Forma padrão de declarar objetos no Java
//        Cliente cliente2 = new Cliente();
//        cliente2.setId(idCliente);

        // Declarando um objeto novo da classe Historico com base em um construtor
        // sob demanda, feito através da anotação 'Builder'
        Historico historico = Historico
                .builder()
                .dataTransacao(LocalDateTime.now())
                .cliente(cliente)
                .produtosHistoricos(produtos)
                .build();

        this.salvar(historico);
    }

    public List<Object[]> historicoClienteProduto() {
        return historicoRepository.findHistoricoClienteProduto();
    }
}