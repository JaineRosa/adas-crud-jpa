package com.adas.crud_jpa.service;

import com.adas.crud_jpa.model.Categoria;
import com.adas.crud_jpa.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria>buscarTodos(){
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(int id){
        return categoriaRepository.findById(id).orElse(null);
    }

    public Categoria salvar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void excluir (Categoria categoria){
        categoriaRepository.delete(categoria);
    }

   public List<Categoria> findByNomeContainingIgnoreCase(String nome){ return categoriaRepository.findByNomeContainingIgnoreCase(nome);}

}
