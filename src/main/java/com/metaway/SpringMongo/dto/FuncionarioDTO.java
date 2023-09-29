package com.metaway.SpringMongo.dto;

import com.metaway.SpringMongo.entities.Funcionario;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDTO implements Serializable {
    private String codigo;
    private String nome;
    private Integer idade;
    private BigDecimal salario;
    private String chefeCodigo;

    private List<PedidoDTO> pedidos = new ArrayList<>();

    public FuncionarioDTO(){}

    public FuncionarioDTO(Funcionario entity){
        this.codigo = entity.getCodigo();
        this.nome = entity.getNome();
        this.idade = entity.getIdade();
        this.salario = entity.getSalario();
        if(entity.getChefe() != null){
            this.chefeCodigo = entity.getChefe().getCodigo();
        }

        entity.getPedidos().forEach(pedido -> this.pedidos.add(new PedidoDTO(pedido)));
    }

    public FuncionarioDTO(String codigo, String nome, Integer idade, BigDecimal salario, String chefeCodigo) {
        this.codigo = codigo;
        this.nome = nome;
        this.idade = idade;
        this.salario = salario;
        this.chefeCodigo = chefeCodigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getChefeCodigo() {
        return chefeCodigo;
    }

    public void setChefeCodigo(String chefeCodigo) {
        this.chefeCodigo = chefeCodigo;
    }

    public List<PedidoDTO> getPedidos() {
        return pedidos;
    }
}
