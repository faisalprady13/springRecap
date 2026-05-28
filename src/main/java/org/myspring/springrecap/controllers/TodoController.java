package org.myspring.springrecap.controllers;

import lombok.RequiredArgsConstructor;
import org.myspring.springrecap.dtos.TodoDTO;
import org.myspring.springrecap.models.Todo;
import org.myspring.springrecap.services.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @GetMapping
    public List<Todo> getTodos() {
        return todoService.getTodos();
    }

    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable UUID id) {
        return todoService.getTodo(id);
    }

    @PostMapping
    public void addTodo(@RequestBody TodoDTO todoDTO) {
        todoService.addTodo(todoDTO);
    }

    @PutMapping("/{id}")
    public void updateTodo(@PathVariable UUID id, @RequestBody TodoDTO todoDTO) {
        todoService.updateTodo(id, todoDTO);
    }
}
