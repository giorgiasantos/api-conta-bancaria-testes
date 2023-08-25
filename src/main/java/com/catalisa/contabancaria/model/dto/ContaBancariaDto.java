package com.catalisa.contabancaria.model.dto;

import com.catalisa.contabancaria.model.ContaBancariaModel;

import java.io.Serializable;

public class ContaBancariaDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String numeroConta;
    private String agencia;
    private String nome;
    private double saldo;

    public ContaBancariaDto(ContaBancariaModel contaBancariaModel) {
        this.id = contaBancariaModel.getId();
        this.numeroConta = contaBancariaModel.getNumeroConta();
        this.agencia = contaBancariaModel.getAgencia();
        this.nome = contaBancariaModel.getNome();
        this.saldo = contaBancariaModel.getSaldo();

    }

    public ContaBancariaDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }


}
