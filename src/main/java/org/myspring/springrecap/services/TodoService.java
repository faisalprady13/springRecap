package org.myspring.springrecap.services;

import lombok.RequiredArgsConstructor;
import org.myspring.springrecap.dtos.TodoDTO;
import org.myspring.springrecap.models.Todo;
import org.myspring.springrecap.repositories.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public List<Todo> getTodos() {
        return todoRepository.findAll();
    }

    public Todo getTodo(UUID todoId) {
        return todoRepository.findById(todoId).orElseThrow();
    }

    public UUID addTodo(TodoDTO todoDTO) {
        Todo newTodo = new Todo(todoDTO);
        todoRepository.save(newTodo);
        return newTodo.id();
    }

    public void updateTodo(UUID id, TodoDTO todoDTO) {
        Todo existingTodo = todoRepository.findById(id).orElseThrow();
        Todo newTodo = Todo.builder()
                .id(existingTodo.id())
                .description(todoDTO.description() == null ? existingTodo.description() : todoDTO.description())
                .status(todoDTO.status() == null ? existingTodo.status() : todoDTO.status())
                .build();

        todoRepository.save(newTodo);
    }


    public void deleteTodo(UUID id) {
        todoRepository.findById(id).orElseThrow();
        todoRepository.deleteById(id);
    }
}
