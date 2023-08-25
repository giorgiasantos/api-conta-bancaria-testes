package com.catalisa.contabancaria.controller;

import com.catalisa.contabancaria.model.ContaBancariaModel;
import com.catalisa.contabancaria.model.dto.ContaBancariaDto;
import com.catalisa.contabancaria.service.ContaBancariaService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ContaBancariaController.class)

class ContaBancariaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContaBancariaService contaBancariaService;

    @Test
    void exibirTodasAsContas() throws Exception {
        ContaBancariaModel conta1 = new ContaBancariaModel();
        conta1.setId(1L);
        conta1.setSaldo(100.0);
        conta1.setAgencia("333-1");
        conta1.setNumeroConta("0010");
        conta1.setNome("Kendall Roy");

        List<ContaBancariaDto> listaDeContas = new ArrayList<>();
        listaDeContas.add(new ContaBancariaDto(conta1));

        when(contaBancariaService.exibirTodas()).thenReturn(listaDeContas);
        mockMvc.perform(get("/contas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].saldo").value(100.0))
                .andExpect(jsonPath("$[0].agencia").value("333-1"))
                .andExpect(jsonPath("$[0].numeroConta").value("0010"))
                .andExpect(jsonPath("$[0].nome").value("Kendall Roy"));
    }

    @Test
    void testeBuscarContaPorId() throws Exception{
        ContaBancariaModel conta1 = new ContaBancariaModel();

        conta1.setId(1L);
        conta1.setSaldo(100.0);
        conta1.setAgencia("333-1");
        conta1.setNumeroConta("0010");
        conta1.setNome("Kendall Roy");


        when(contaBancariaService.buscarPorId(conta1.getId())).thenReturn(Optional.of(new ContaBancariaDto(conta1)));
        mockMvc.perform(get("/contas/{id}",1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.saldo").value(100.0))
                .andExpect(jsonPath("$.agencia").value("333-1"))
                .andExpect(jsonPath("$.numeroConta").value("0010"))
                .andExpect(jsonPath("$.nome").value("Kendall Roy"));
    }

    @Test
    void testeCadastrarNovaConta() throws Exception{

        mockMvc.perform(post("/contas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"1\", \"numeroConta\": \"0090\",\"agencia\": \"3333-0\",\"nome\": \"Arya Stark\",\"saldo\": 1060.0}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Conta-corrente criada com sucesso."));
    }

    @Test
    void testeNaoEncontrarContaParaFazerDeposito() throws Exception{
        when(contaBancariaService.depositar(anyLong(),anyDouble())).thenReturn(null);

        mockMvc.perform(put("/contas/deposito/{id}",anyLong())
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(anyLong())))
                .andExpect(status().isNotFound());
    }


    @Test
    void testeFazerDeposito() throws Exception{

        ContaBancariaModel conta = new ContaBancariaModel();

        when(contaBancariaService.depositar(anyLong(),anyDouble())).thenReturn(conta);

        mockMvc.perform(put("/contas/deposito/{id}",anyLong())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(anyLong())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(conta.getId()))
                .andExpect(jsonPath("$.numeroConta").value(conta.getNumeroConta()))
                .andExpect(jsonPath("$.agencia").value(conta.getAgencia()))
                .andExpect(jsonPath("$.nome").value(conta.getNome()))
                .andExpect(jsonPath("$.saldo").value(conta.getSaldo()))
                .andExpect(jsonPath("$.valorFornecido").value(conta.getValorFornecido()))
                .andExpect(jsonPath("$.tipoServico").value(conta.getTipoServico()));

        verify(contaBancariaService, times(1)).depositar(anyLong(), anyDouble());

    }

    @Test
    void testeNaoEncontrarContaParaFazerSaque() throws Exception{

        when(contaBancariaService.sacar(anyLong(),anyDouble())).thenReturn(null);

        mockMvc.perform(put("/contas/saque/{id}",anyLong())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(anyLong())))
                .andExpect(status().isNotFound());
    }

    @Test
    void testefazerSaque() throws Exception{

        ContaBancariaModel conta = new ContaBancariaModel();

        when(contaBancariaService.sacar(anyLong(),anyDouble())).thenReturn(conta);

        mockMvc.perform(put("/contas/saque/{id}",anyLong())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(anyLong())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(conta.getId()))
                .andExpect(jsonPath("$.numeroConta").value(conta.getNumeroConta()))
                .andExpect(jsonPath("$.agencia").value(conta.getAgencia()))
                .andExpect(jsonPath("$.nome").value(conta.getNome()))
                .andExpect(jsonPath("$.saldo").value(conta.getSaldo()))
                .andExpect(jsonPath("$.valorFornecido").value(conta.getValorFornecido()))
                .andExpect(jsonPath("$.tipoServico").value(conta.getTipoServico()));

        verify(contaBancariaService, times(1)).sacar(anyLong(), anyDouble());

    }


    @Test
    void testeExcluirConta() throws Exception{
        mockMvc.perform(delete("/contas/{id}",anyLong()))
                .andExpect(status().isOk());
    }
}