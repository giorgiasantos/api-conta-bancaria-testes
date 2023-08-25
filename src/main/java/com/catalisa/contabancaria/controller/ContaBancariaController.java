package com.catalisa.contabancaria.controller;

import com.catalisa.contabancaria.model.ContaBancariaModel;
import com.catalisa.contabancaria.model.dto.ContaBancariaDto;
import com.catalisa.contabancaria.service.ContaBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ContaBancariaController {
    @Autowired
    ContaBancariaService contaBancariaService;

    //ENDPOINTS
    //REQUISIÇÕES GET

    //método para mostrar todas as contas
    @GetMapping(path = "/contas")
    public Iterable<ContaBancariaDto> exibirTodasAsContas(){
        return contaBancariaService.exibirTodas();
    }

    //método para mostrar uma conta específica
    @GetMapping (path = "/contas/{id}")
    public Optional<ContaBancariaDto> buscarContaPorId(@PathVariable Long id){
        return contaBancariaService.buscarPorId(id);
    }

    // REQUISIÇÃO POST
    //método para cadastrar uma nova conta
    @PostMapping(path = "/contas")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> cadastrarNovaConta(@RequestBody ContaBancariaDto contaBancariaDto){
        contaBancariaService.cadastrar(contaBancariaDto);
        return ResponseEntity.ok("Conta-corrente criada com sucesso.");
    }

    //REQUISIÇÃO PUT
    // método para atualizar valor por depósito
    @PutMapping (path = "/contas/deposito/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ContaBancariaModel> fazerDeposito(@PathVariable Long id, @RequestBody double valorFornecido){
        ContaBancariaModel deposito = contaBancariaService.depositar(id,valorFornecido);

        if(deposito != null){
            return ResponseEntity.ok(deposito);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    // método para atualizar valor por saque
    @PutMapping (path = "/contas/saque/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ContaBancariaModel> fazerSaque(@PathVariable Long id, @RequestBody double valorFornecido){

        ContaBancariaModel saque = contaBancariaService.sacar(id,valorFornecido);

        if(saque != null){
            return ResponseEntity.ok(saque);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //REQUISIÇÃO DELETE
    // método para excluir uma conta bancária
    @DeleteMapping(path = "/contas/{id}")
    public void excluirConta(@PathVariable Long id){
        contaBancariaService.excluir(id);
    }
}
