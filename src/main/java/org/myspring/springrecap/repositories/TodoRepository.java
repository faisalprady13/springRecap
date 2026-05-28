package org.myspring.springrecap.repositories;

import lombok.Builder;
import org.myspring.springrecap.models.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TodoRepository extends MongoRepository<Todo, UUID> {
}
