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

    @GetMapping("/todas")
    public ResponseEntity<List<Categoria>> listarTodasCategorias() {

        return ResponseEntity.ok(categoriaService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria>buscarPorId(@PathVariable int id){
        Categoria categoriaEncontrada=categoriaService.buscarPorId(id);

        if(categoriaEncontrada==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoriaEncontrada);
    }

    @GetMapping("/nome/{nome}")
        public List<Categoria> findByNome(@PathVariable String nome){
        return categoriaService.findByNomeContainingIgnoreCase(nome);
        }



    @PostMapping("/nova")
    public ResponseEntity<Categoria> cadastrarNovaCategoria(@RequestBody Categoria categoria) {

        return ResponseEntity.ok(categoriaService.salvar(categoria));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Categoria>alterar(@RequestBody Categoria categoria, @PathVariable int id){
        Categoria categoriaEncontrada = categoriaService.buscarPorId(id);

        if (categoriaEncontrada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoriaService.salvar(categoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Categoria> excluir (@PathVariable int id){
        Categoria categoriaEncontrada = categoriaService.buscarPorId(id);

        if (categoriaEncontrada == null){
            return ResponseEntity.notFound().build();
        }
        categoriaService.excluir(categoriaEncontrada);
        return ResponseEntity.ok(categoriaEncontrada);

    }
}