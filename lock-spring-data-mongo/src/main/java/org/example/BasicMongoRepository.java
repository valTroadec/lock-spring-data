package org.example;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BasicMongoRepository extends MongoRepository<CarEntity, String> {

}
