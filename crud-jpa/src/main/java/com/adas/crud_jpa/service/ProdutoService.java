package com.adas.crud_jpa.service;

import com.adas.crud_jpa.model.Produto;
import com.adas.crud_jpa.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> findAll(){
        return produtoRepository.findAll();
    }

    public Produto findById(int id){
        return produtoRepository.findById(id).orElse(null);
    }

    public Produto save(Produto produto) {
        return produtoRepository.save(produto);
    }

    public void delete (Produto produto){
        produtoRepository.delete(produto);
    }

    public List<Produto> findByExactNome(String nome) {
        return produtoRepository.findByExactNome(nome);
    }

    public List<Produto> findBySimilarNome(String nome) {
        return produtoRepository.findBySimilarNome(nome);
    }

    public List<Produto> findByPrecoMaiorQue(Double preco) {
        return produtoRepository.findByPrecoMaiorQue(preco);
    }

    public List<Produto> findbyCategoriaPorCodigo(Integer codigo) {
        return produtoRepository.findbyCategoriaPorCodigo(codigo);
    }

    public List<Produto> findProdutosByCategoriaNome(String categoriaNome){
        return produtoRepository.findProdutosByCategoriaNome(categoriaNome);
    }
}
