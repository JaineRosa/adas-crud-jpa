package com.adas.crud_jpa.controller;

import com.adas.crud_jpa.model.Categoria;
import com.adas.crud_jpa.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    public void cadastrar(@RequestBody Categoria categoria){

            categoriaService.salvar(categoria);

    }

    @GetMapping()
    public List<Categoria> buscarTodos(){
            return  categoriaService.buscarTodos();
    }

}
