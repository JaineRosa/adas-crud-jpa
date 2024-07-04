package com.adas.crud_jpa.controller;

import com.adas.crud_jpa.model.Categoria;
import com.adas.crud_jpa.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    public Categoria cadastrar(@RequestBody Categoria novaCategoria){
        return categoriaService.salvar(novaCategoria);
    }
    @GetMapping()
    public List<Categoria> buscarTodos(){
            return  categoriaService.buscarTodos();
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Categoria> buscarPorId(@PathVariable int id){
        Categoria categoriaEncontrada= categoriaService.buscarPorId(id);
        if( categoriaEncontrada == null){
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.ok(categoriaEncontrada);
    }
}

