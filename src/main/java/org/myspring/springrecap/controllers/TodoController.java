package org.myspring.springrecap.controllers;

import lombok.RequiredArgsConstructor;
import org.myspring.springrecap.dtos.TodoDTO;
import org.myspring.springrecap.models.Todo;
import org.myspring.springrecap.services.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @GetMapping

    public ResponseEntity<List<Todo>> getTodos() {
        return ResponseEntity.ok(todoService.getTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodo(@PathVariable UUID id) {
        return ResponseEntity.ok(todoService.getTodo(id));
    }

    @PostMapping
    public ResponseEntity<Void> addTodo(@RequestBody TodoDTO todoDTO) {
        UUID id = todoService.addTodo(todoDTO);
        return ResponseEntity.created(URI.create("/api/todo/" + id)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTodo(@PathVariable UUID id, @RequestBody TodoDTO todoDTO) {
        todoService.updateTodo(id, todoDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable UUID id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

}
