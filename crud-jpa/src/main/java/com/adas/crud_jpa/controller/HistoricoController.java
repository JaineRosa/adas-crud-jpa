package com.adas.crud_jpa.controller;

import com.adas.crud_jpa.model.Historico;
import com.adas.crud_jpa.service.HistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historico")
public class HistoricoController {
    @Autowired
    private HistoricoService historicoService;

    @GetMapping("/todos")
    public ResponseEntity<List<Historico>> listarTodosHistoricos() {

        return ResponseEntity.ok(historicoService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Historico>buscarPorId(@PathVariable int id){
        Historico historicoEncontrado=historicoService.buscarPorId(id);

        if(historicoEncontrado==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(historicoEncontrado);
    }

    @PostMapping("/novo")
    public ResponseEntity<Historico> cadastrarNovoHistorico(@RequestBody Historico historico) {

        return ResponseEntity.ok(historicoService.salvar(historico));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Historico>alterar(@RequestBody Historico historico, @PathVariable int id){
        Historico historicoEncontrado = historicoService.buscarPorId(id);

        if (historicoEncontrado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(historicoService.salvar(historico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Historico> excluir (@PathVariable int id){
        Historico historicoEncontrado = historicoService.buscarPorId(id);

        if (historicoEncontrado == null){
            return ResponseEntity.notFound().build();
        }
        historicoService.excluir(historicoEncontrado);
        return ResponseEntity.ok(historicoEncontrado);

    }
}

