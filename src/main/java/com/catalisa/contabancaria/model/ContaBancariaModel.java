package com.catalisa.contabancaria.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tb_conta_corrente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ContaBancariaModel {

    //chave primária
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //coluna numero da conta
    @Column(length = 20, nullable = false)
    private String numeroConta;

    //coluna agencia
    @Column(length = 6, nullable = false)
    private String agencia;

    //coluna nome
    @Column(length = 200, nullable = false)
    private String nome;

    //coluna saldo
    @Column (length = 40,nullable = false)
    private double saldo;

    // coluna valor fornecido
    @Column (length = 40,nullable = false)
    private double valorFornecido;

    //coluna tipo de serviço
    @Column(length = 200, nullable = false)
    private String tipoServico;

}
