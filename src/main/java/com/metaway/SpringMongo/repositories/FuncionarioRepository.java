package com.metaway.SpringMongo.repositories;

import com.metaway.SpringMongo.entities.Funcionario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface FuncionarioRepository extends MongoRepository<Funcionario, String> {

    @Query("{ $and: [ { 'idade': { $gte: ?0 } }, { 'idade': {$lte: ?1 } } ] }") // gt = greater than, lt = less than, gte = greater than equal
    public List<Funcionario> getFuncionariosByIdadeRange(Integer from, Integer to);

    Boolean existsByCodigo(String codigo);

    void deleteByCodigo(String codigo);
}
