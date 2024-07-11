package com.adas.crud_jpa.controller;

import com.adas.crud_jpa.model.Caixa;
import com.adas.crud_jpa.model.Categoria;
import com.adas.crud_jpa.model.Produto;
import com.adas.crud_jpa.service.CaixaService;
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
    @Autowired
    private CaixaService caixaService;

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

    @PutMapping("/vender/{idCaixa}/{quantidadeVendida}")
    public ResponseEntity<String> vender(
            @RequestBody Produto produto,
            @PathVariable int quantidadeVendida,
            @PathVariable int idCaixa){

        Caixa caixa = caixaService.buscarPorId(idCaixa);
        if (!caixa.isStatus()) {
            return ResponseEntity.ok("O caixa com código " + idCaixa + "está fechado. não é possivel realizar venda!");
        }

        //busca o objeto atualizado no banco de dados com base no id informado no corpo da requisicao
        Produto produtoAtual = produtoService.buscarPorId(produto.getId());

        //validar a quantidade de estoque para realizar ou nao a venda do produto.

        if (produtoAtual.getQuantidade() < quantidadeVendida){
            return ResponseEntity.ok("estoque insulficiente para venda do produto");
        }

        //atualizando a quantidade do produto, subtraindo com base na quantidade  recebida na requisição
        produtoAtual.setQuantidade(produtoAtual.getQuantidade() - quantidadeVendida);
        produtoService.salvar(produtoAtual);

        //descobrir o valor total da venda
        Double totalVenda = quantidadeVendida * produtoAtual.getPreco();

        //atualizar o saldo do caixa

        caixa = caixaService.realizarMovimentacao(idCaixa,totalVenda ,"ENTRADA");

        //Concatenando os valores para montar um recibo da movimentação, tanto no produto quanto no caixa.
        String recibo = "Produto vendido: " + produtoAtual.getNome() +
                "\n Total da venda: " + totalVenda +
                "\n Caixa atualizado " + idCaixa +
                "\n Saldo atual do caixa: " + caixa.getSaldo();

        return ResponseEntity.ok(recibo);

    }



    @PutMapping("/comprar/{idCaixa}/{quantidade}")
    public ResponseEntity<String> comprar(
            @RequestBody Produto produto,
            @PathVariable int quantidade,
            @PathVariable int idCaixa){

       //verificando se o caixa esta ativo

        Caixa caixa = caixaService.buscarPorId(idCaixa);
        if (!caixa.isStatus()) {
            return ResponseEntity.ok("O caixa com código " + idCaixa + "está fechado! Não é possivel comprar.");
        }
        Produto produtoAtual = produtoService.buscarPorId(produto.getId());

        Double totalCompra = quantidade * produtoAtual.getPreco();

        if (caixa.getSaldo() < totalCompra){
            return ResponseEntity.ok("Saldo insuficiente para realizar a compra!");
        }
        produtoAtual.setQuantidade(produtoAtual.getQuantidade() + quantidade);
        produtoService.salvar(produtoAtual);

        caixa = caixaService.realizarMovimentacao(idCaixa,totalCompra ,"SAIDA");

        String recibo = "Produto Comprado: " + produtoAtual.getNome() +
                "\n Total da Compra: " + totalCompra +
                "\n Caixa atualizado " + idCaixa +
                "\n Saldo atual do caixa: " + caixa.getSaldo();

        return ResponseEntity.ok(recibo);

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



}
