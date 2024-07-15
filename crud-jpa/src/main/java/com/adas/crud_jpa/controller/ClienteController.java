package com.adas.crud_jpa.controller;

import com.adas.crud_jpa.model.Cliente;
import com.adas.crud_jpa.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/todos")
    public ResponseEntity<List<Cliente>> listarTodosClientes() {

        return ResponseEntity.ok(clienteService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente>buscarPorId(@PathVariable int id){
        Cliente clienteEncontrado=clienteService.buscarPorId(id);

        if(clienteEncontrado==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clienteEncontrado);
    }

    @PostMapping("/novo")
    public ResponseEntity<Cliente> cadastrarNovoCliente(@RequestBody Cliente cliente) {

        return ResponseEntity.ok(clienteService.salvar(cliente));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Cliente>alterar(@RequestBody Cliente cliente, @PathVariable int id){
        Cliente clienteEncontrado = clienteService.buscarPorId(id);

        if (clienteEncontrado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clienteService.salvar(cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cliente> excluir (@PathVariable int id){
        Cliente clienteEncontrado = clienteService.buscarPorId(id);

        if (clienteEncontrado == null){
            return ResponseEntity.notFound().build();
        }
        clienteService.excluir(clienteEncontrado);
        return ResponseEntity.ok(clienteEncontrado);

    }
}
