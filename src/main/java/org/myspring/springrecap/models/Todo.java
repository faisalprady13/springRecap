package org.myspring.springrecap.models;

import lombok.Builder;
import org.myspring.springrecap.dtos.TodoDTO;
import org.myspring.springrecap.enums.Status;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Builder
@Document("todos")
public record Todo(UUID id, String description, Status status) {
    public Todo(TodoDTO todoDTO) {
        this(UUID.randomUUID(), todoDTO.description(), todoDTO.status());
    }
}
