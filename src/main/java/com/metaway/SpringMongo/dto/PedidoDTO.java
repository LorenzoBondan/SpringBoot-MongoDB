package com.metaway.SpringMongo.dto;

import com.metaway.SpringMongo.entities.Pedido;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoDTO implements Serializable {
    private String codigo;
    private String descricao;
    private LocalDate dataCriacao;
    private String funcionarioCodigo;

    private List<ItemDTO> itens = new ArrayList<>();

    public PedidoDTO(){}

    public PedidoDTO(Pedido entity){
        this.codigo = entity.getCodigo();
        this.descricao = entity.getDescricao();
        this.dataCriacao = entity.getDataCriacao();
        this.funcionarioCodigo = entity.getFuncionario().getCodigo();

        entity.getItens().forEach(item -> this.itens.add(new ItemDTO(item)));
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getFuncionarioCodigo() {
        return funcionarioCodigo;
    }

    public void setFuncionarioCodigo(String funcionarioCodigo) {
        this.funcionarioCodigo = funcionarioCodigo;
    }

    public List<ItemDTO> getItens() {
        return itens;
    }
}
