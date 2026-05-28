package org.myspring.springrecap.controllers;

import org.junit.jupiter.api.Test;
import org.myspring.springrecap.enums.Status;
import org.myspring.springrecap.models.Todo;
import org.myspring.springrecap.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepository mockTodoRepository;

    @Test
    void getTodos_returnEmpty_initially() throws Exception {
        mockMvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void getTodo_shouldReturnAllTodos() throws Exception {

        Todo todo1 = Todo.builder()
                .id(UUID.randomUUID())
                .description("todo 1")
                .status(Status.OPEN)
                .build();
        Todo todo2 = Todo.builder()
                .id(UUID.randomUUID())
                .description("todo 2")
                .status(Status.OPEN)
                .build();
        mockTodoRepository.save(todo1);
        mockTodoRepository.save(todo2);

        mockMvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].description").value("todo 1"))
                .andExpect(jsonPath("$[1].description").value("todo 2"));
    }

    @Test
    void addTodo() {
    }

    @Test
    void updateTodo() {
    }

    @Test
    void deleteTodo() {
    }
}