package com.metaway.SpringMongo.dto;

import com.metaway.SpringMongo.entities.Item;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ItemDTO implements Serializable {
    private String id;
    private String descricao;
    private BigDecimal preco;
    private List<String> pedidosCodigos = new ArrayList<>();

    public ItemDTO(){}

    public ItemDTO(Item entity){
        this.id = entity.getId();
        this.descricao = entity.getDescricao();
        this.preco = entity.getPreco();

        entity.getPedidos().forEach(pedido -> this.pedidosCodigos.add(pedido.getCodigo()));
    }

    public ItemDTO(String id, String descricao, BigDecimal preco) {
        this.id = id;
        this.descricao = descricao;
        this.preco = preco;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public List<String> getPedidosCodigos() {
        return pedidosCodigos;
    }
}
