package com.adas.crud_jpa.controller;

import com.adas.crud_jpa.model.Caixa;
import com.adas.crud_jpa.service.CaixaService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/caixa")
public class CaixaController {
    
    @Autowired
    private CaixaService caixaService;

    @PostMapping
    public ResponseEntity<Caixa> cadastrar(@RequestBody Caixa novoCaixa){
        return ResponseEntity.ok(caixaService.salvar(novoCaixa));
    }

    @GetMapping
    public ResponseEntity<List<Caixa>> buscarTodos(){
        return ResponseEntity.ok(caixaService.buscarTodos());
    }

    @GetMapping("/{id}")

    public ResponseEntity<Caixa> buscarPorId(@PathVariable int id){
        Caixa caixaEncontrado =  caixaService.buscarPorId(id);
        if (caixaEncontrado ==null){
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(caixaEncontrado);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<Caixa>alterar(@PathVariable int id, @RequestBody Caixa caixaEditado){
        Caixa caixaEncontrado = caixaService.buscarPorId(id);
        if (caixaEncontrado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(caixaService.salvar(caixaEditado));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Caixa>excluir(@PathVariable int id){
        Caixa caixaEncontrado = caixaService.buscarPorId(id);
        if (caixaEncontrado == null){
            return ResponseEntity.notFound().build();
        }
        caixaService.excluir(caixaEncontrado);
        return ResponseEntity.ok(caixaEncontrado);
    }




}
