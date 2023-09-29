package com.metaway.SpringMongo.repositories;

import com.metaway.SpringMongo.entities.Pedido;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PedidoRepository extends MongoRepository<Pedido, String> {

    Boolean existsByCodigo(String codigo);

    void deleteByCodigo(String codigo);
}
