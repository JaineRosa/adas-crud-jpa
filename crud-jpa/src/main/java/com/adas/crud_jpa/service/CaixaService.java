package com.adas.crud_jpa.service;

import com.adas.crud_jpa.model.Caixa;
import com.adas.crud_jpa.repository.CaixaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaixaService {

    @Autowired
    private CaixaRepository caixaRepository;

    public List<Caixa> buscarTodos(){
        return caixaRepository.findAll();
    }
    public Caixa buscarPorId(Integer id){
        return  caixaRepository.findById(id).orElse(null);
    }
    public Caixa salvar(Caixa caixa){
        return caixaRepository.save(caixa);
    }
    public  void excluir(Caixa caixa){
        caixaRepository.delete(caixa);
    }

    public Caixa realizarMovimentacao(int id, Double valor, String acao){
        Caixa caixa = buscarPorId(id);

        if(acao.equalsIgnoreCase("SAIDA")){
            caixa.setSaldo(caixa.getSaldo() - valor);
        } else if (acao.equalsIgnoreCase("ENTRADA")){
            caixa.setSaldo(caixa.getSaldo() + valor);
        }
      return  salvar(caixa);
    }




}
