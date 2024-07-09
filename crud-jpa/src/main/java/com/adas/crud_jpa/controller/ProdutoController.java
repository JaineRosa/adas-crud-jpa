package com.adas.crud_jpa.controller;

import com.adas.crud_jpa.model.Categoria;
import com.adas.crud_jpa.model.Produto;
import com.adas.crud_jpa.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
   public ResponseEntity<Produto> cadastrar(@RequestBody Produto novoProduto){
        return ResponseEntity.ok(produtoService.salvar(novoProduto));
        }

        @GetMapping
    public ResponseEntity<List<Produto>> buscarTodos(){
        return ResponseEntity.ok(produtoService.buscarTodos());
        }

        @GetMapping("/{id}")

        public ResponseEntity<Produto> buscarPorId(@PathVariable int id){
        Produto produtoEncontrado =  produtoService.buscarPorId(id);
        if (produtoEncontrado ==null){
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(produtoEncontrado);
        }

    @PutMapping("/{id}")
    public  ResponseEntity<Produto>alterar(@PathVariable int id, @RequestBody Produto produtoEditado){
       Produto produtoEncontrado = produtoService.buscarPorId(id);
        if (produtoEncontrado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(produtoService.salvar(produtoEditado));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Produto>excluir(@PathVariable int id){
        Produto produtoEncontrado = produtoService.buscarPorId(id);
        if (produtoEncontrado == null){
            return ResponseEntity.notFound().build();
        }
        produtoService.excluir(produtoEncontrado);
        return ResponseEntity.ok(produtoEncontrado);
    }


    @PostMapping("/vender/{id}/{quantidadeVendida}")
    public ResponseEntity<Produto> vender(@PathVariable int quantidadeVendida,@PathVariable int id){
        Produto produtoVendido = produtoService.buscarPorId(id);
        if (produtoVendido == null){
            ResponseEntity.notFound().build();
        }
        produtoVendido.setQuantidade(produtoVendido.getQuantidade() - quantidadeVendida);
        produtoService.salvar(produtoVendido);
        return ResponseEntity.ok(produtoVendido);
    }

    @PostMapping("/comprar/{id}/{quantidadeCompra}")
    public ResponseEntity<Produto> comprar(@PathVariable int quantidadeCompra,@PathVariable int id){
        Produto produtoComprado = produtoService.buscarPorId(id);
        if (produtoComprado == null){
            ResponseEntity.notFound().build();
        }
        assert produtoComprado != null;
        produtoComprado.setQuantidade(produtoComprado.getQuantidade() + quantidadeCompra);

        produtoService.salvar(produtoComprado);
        return ResponseEntity.ok(produtoComprado);
    }
}
