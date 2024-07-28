package com.adas.crud_jpa.controller;

import com.adas.crud_jpa.model.*;
import com.adas.crud_jpa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CaixaService caixaService;

    @Autowired
    private HistoricoService historicoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CategoriaService categoriaService;


    @GetMapping("/todos")
    public ResponseEntity<List<Produto>> findAll() {

        return ResponseEntity.ok(produtoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto>findById(@PathVariable int id){
        Produto produtoEncontrado=produtoService.findById(id);

        if(produtoEncontrado==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(produtoEncontrado);
    }

    @GetMapping("/nome/exato/{nome}")
    public ResponseEntity<List<Produto>> findByExactNome(@PathVariable String nome){
        return ResponseEntity.ok(produtoService.findByExactNome(nome));
    }

    @GetMapping("/nome/similar/{nome}")
    public ResponseEntity<List<Produto>> findBySimilarNome(@PathVariable String nome){
        return ResponseEntity.ok(produtoService.findBySimilarNome(nome));
    }

    @GetMapping("/preco/{valor}")
    public ResponseEntity<List<Produto>> findByPrecoMaiorQue(@PathVariable Double valor){
        return ResponseEntity.ok(produtoService.findByPrecoMaiorQue(valor));
    }

    @GetMapping("/categoria/{codigo}")
    public ResponseEntity<List<Produto>> findbyCategoriaPorCodigo(@PathVariable Integer codigo) {

            return ResponseEntity.ok(produtoService.findbyCategoriaPorCodigo(codigo));
    }

    @GetMapping("/categoria/ativa/{codigo}")
    public ResponseEntity<?> findbyCategoriaPorCodigoAtivo(@PathVariable Integer codigo) {
    Categoria categoria = categoriaService.buscarPorId(codigo);

    //validndo se a categoria existe no banco de dados.
    if (categoria == null){
        return ResponseEntity.status(500).body("Categoria não encontrada para o código: " + codigo);
    }

    //validando se o status  da categoria é true
    if (categoria.isStatus()== false){
        return ResponseEntity.status(500).body("Categoria " + categoria.getNome()+ " está inativa!");
    }

        return ResponseEntity.ok(produtoService.findbyCategoriaPorCodigo(codigo));
    }

    @PostMapping("/novo")
    public ResponseEntity<Produto> add(@RequestBody Produto produto) {

        return ResponseEntity.ok(produtoService.save(produto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto>update(@RequestBody Produto produto, @PathVariable int id){
        Produto produtoEncontrado = produtoService.findById(id);

        if (produtoEncontrado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(produtoService.save(produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Produto>delete (@PathVariable int id){
        Produto produtoEncontrado = produtoService.findById(id);

        if (produtoEncontrado == null){
            return ResponseEntity.notFound().build();
        }
        produtoService.delete(produtoEncontrado);
        return ResponseEntity.ok(produtoEncontrado);

    }

    @PutMapping("/vender/{quantidade}/{idCaixa}/{idCliente}")
    public ResponseEntity<String> vender(@RequestBody Produto produto,
                                         @PathVariable int quantidade,
                                         @PathVariable int idCaixa,
                                         @PathVariable int idCliente) {

        // Verificar se o caixa está ativo
        Caixa caixa = caixaService.findById(idCaixa);
        if (!caixa.isStatus()) {
            return ResponseEntity.ok("O caixa de código "+ idCaixa + " está fechado! Nâo é possível realizar venda.");
        }
        // Busca o objeto atualizado no banco de dados com base no id informado no corpo da requisição
        Produto produtoAtual = produtoService.findById(produto.getId());
        // Validar a quantidade de estoque para realizar ou não a venda do produto
        if (produtoAtual.getQuantidade() < quantidade) {
            return ResponseEntity.ok("Estoque insulficiente para venda do produto.");
        }
        // Atualizando a quantidade do produto, subtraindo com base na quantidade recebida na requisição
        produtoAtual.setQuantidade(produtoAtual.getQuantidade() - quantidade);
        produtoService.save(produtoAtual);
        // Descobrir o valor total da venda
        Double totalVenda = quantidade * produtoAtual.getPreco();

        // Criando a lista de produtos para enviar para o histórico
        List<Produto> produtos = new ArrayList<>();
        produtos.add(produto);

        // Atualizar o saldo do caixa
        caixa = caixaService.realizarMovimentacao(idCaixa, totalVenda, "ENTRADA");

        // Registrar movimentção no histórico
        historicoService.registrarVenda(idCliente, produtos);

        // Concatenando os valores para montar um recibo da movimentação, tanto no produto quanto no caixa
        String recibo = " " +
                "Produto vendido: " + produtoAtual.getNome() +
                "\nTotal da venda: R$ " + totalVenda +
                "\nCaixa atualizado: " + idCaixa +
                "\nSaldo atual do caixa: R$ " + caixa.getSaldo();
        return ResponseEntity.ok(recibo);
    }


    @PutMapping("/vender/lista/{idCliente}")
    public ResponseEntity<String> venderLista(@RequestBody List<Produto> produtos,
                                              @PathVariable int idCliente) {

        historicoService.registrarVenda(idCliente, produtos);
        return ResponseEntity.ok("Venda registrada com sucesso!");
    }

    @PostMapping("/comprar/{quantidade}/{idCaixa}")
    public ResponseEntity<String> comprarProduto(@RequestBody  Produto produto, @PathVariable int quantidade, @PathVariable  int idCaixa) {

        //Busca o objeto atualizado no banco de dados com base no id informado no corpo da requisiçao

        Caixa caixa = caixaService.findById(idCaixa);
        if (!caixa.isStatus()) {
            return ResponseEntity.ok("Não foi possível realizar a compra pois o caixa "+ idCaixa + " está fechado.");

        }

        Produto produtoAtual = produtoService.findById(produto.getId());
        //Valor compra
        double valorCompra= produto.getPreco()*quantidade;

        if (caixa.getSaldo()<valorCompra){
            return ResponseEntity.ok("Saldo insufieciente para compra.");
        }

        produtoAtual.setQuantidade(produtoAtual.getQuantidade()+quantidade);
        produtoService.save(produtoAtual);

        //atualizar o saldo do caixa
        caixa = caixaService.realizarMovimentacao(idCaixa, valorCompra, "saida");

        String recibo = "" +
                "Produto comprado: "+ produtoAtual.getNome() +
                "\n Valor Total da compra: R$ " + valorCompra +
                "\n Caixa atualizado: " + idCaixa +
                "\n Saldo atual do caixa: " + caixa.getSaldo();
        return ResponseEntity.ok(recibo);
    }

}

