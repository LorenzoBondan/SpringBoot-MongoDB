package com.metaway.SpringMongo.repositories;

import com.metaway.SpringMongo.entities.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, String> {
}
