package com.catalisa.contabancaria.service;

import com.catalisa.contabancaria.model.ContaBancariaModel;
import com.catalisa.contabancaria.model.dto.ContaBancariaDto;
import com.catalisa.contabancaria.repository.ContaBancariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContaBancariaService {

    //ATRIBUTO
    @Autowired
    ContaBancariaRepository contaBancariaRepository;

    //MÉTODOS

    // exibir todas as contas
    public Iterable<ContaBancariaDto> exibirTodas(){
        Iterable<ContaBancariaModel> contas = contaBancariaRepository.findAll();
        List<ContaBancariaDto> listaContasDto = new ArrayList<>();

        for(ContaBancariaModel conta: contas){
            listaContasDto.add(new ContaBancariaDto(conta));
        }
        return listaContasDto;
    }

    // buscar uma conta por id
    public Optional<ContaBancariaDto> buscarPorId(Long id){
        Optional<ContaBancariaModel> conta = contaBancariaRepository.findById(id);
        if (conta.isPresent())
            return Optional.of(new ContaBancariaDto(conta.get()));
        return Optional.empty();
    }

    // cadastrar nova conta
    public ContaBancariaModel cadastrar(ContaBancariaDto contaBancariaDto){
        ContaBancariaModel contaBancariaModel = new ContaBancariaModel();

        contaBancariaModel.setId(contaBancariaDto.getId());
        contaBancariaModel.setNome(contaBancariaDto.getNome());
        contaBancariaModel.setNumeroConta(contaBancariaDto.getNumeroConta());
        contaBancariaModel.setAgencia(contaBancariaDto.getAgencia());
        contaBancariaModel.setSaldo(contaBancariaDto.getSaldo());
        contaBancariaModel.setValorFornecido(0.0);
        contaBancariaModel.setTipoServico("Criação de conta-corrente");

        return contaBancariaRepository.save(contaBancariaModel);
    }

    //fazer deposito
    public ContaBancariaModel depositar(Long id, double valorFornecido){
        ContaBancariaModel conta = contaBancariaRepository.findById(id).orElse(null);

        if(conta != null){
            double saldoFinal = conta.getSaldo() + valorFornecido;
            conta.setValorFornecido(valorFornecido);
            conta.setSaldo(saldoFinal);
            conta.setTipoServico("Depósito");
            return contaBancariaRepository.save(conta);
        }
        return null;
    }

    //fazer saque
    public ContaBancariaModel sacar(Long id, double valorFornecido){
        ContaBancariaModel conta = contaBancariaRepository.findById(id).orElse(null);

        if(conta != null && conta.getSaldo() >= valorFornecido){
            conta.setValorFornecido(valorFornecido);
            double saldoFinal = conta.getSaldo() - valorFornecido;
            conta.setSaldo(saldoFinal);
            conta.setTipoServico("Saque");
            return contaBancariaRepository.save(conta);
        }
        return null;
    }

    // excluir uma conta
    public void excluir(Long id){
        contaBancariaRepository.deleteById(id);
    }

}
