package com.metaway.SpringMongo.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Document // @Table, sem collection = "" ele cria com o nome da entidade
public class Funcionario {
    @Id
    private String codigo;
    private String nome;
    private Integer idade;
    private BigDecimal salario;
    @DBRef // serve para qualquer tipo de relacionamento
    private Funcionario chefe;

    @DBRef
    private List<Pedido> pedidos = new ArrayList<>();

    public Funcionario(){}

    public Funcionario(String codigo, String nome, Integer idade, BigDecimal salario, Funcionario chefe) {
        this.codigo = codigo;
        this.nome = nome;
        this.idade = idade;
        this.salario = salario;
        this.chefe = chefe;
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

    public Funcionario getChefe() {
        return chefe;
    }

    public void setChefe(Funcionario chefe) {
        this.chefe = chefe;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void addPedido(Pedido pedido){
        pedidos.add(pedido);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Funcionario that = (Funcionario) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
