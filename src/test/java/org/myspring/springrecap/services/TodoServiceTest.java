package org.myspring.springrecap.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.myspring.springrecap.dtos.TodoDTO;
import org.myspring.springrecap.enums.Status;
import org.myspring.springrecap.models.Todo;
import org.myspring.springrecap.repositories.TodoRepository;
import org.springframework.test.annotation.DirtiesContext;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TodoServiceTest {

    @Mock
    private TodoRepository mockTodoRepository;

    @Captor
    ArgumentCaptor<Todo> captor =
            ArgumentCaptor.forClass(Todo.class);

    @Test
    void getTodos_returnAllTodos() {
        TodoService todoService = new TodoService(mockTodoRepository);
        Todo todo1 = Todo.builder().status(Status.OPEN).description("todo1").id(UUID.randomUUID()).build();
        List<Todo> todoList = new ArrayList<>(List.of(todo1));
        when(mockTodoRepository.findAll()).thenReturn(new ArrayList<>(List.of(todo1)));

        assertEquals(todoList, todoService.getTodos());
    }


    @Test
    void getTodo_returnSelectedTodo_whenGivenId() {
        TodoService todoService = new TodoService(mockTodoRepository);
        UUID todoId = UUID.randomUUID();
        Todo todo1 = Todo.builder().status(Status.OPEN).description("todo1").id(todoId).build();
        when(mockTodoRepository.findById(todoId)).thenReturn(Optional.ofNullable(todo1));

        assertEquals(todo1, todoService.getTodo(todoId));
        verify(mockTodoRepository).findById(todoId);
    }


    @Test
    void getTodo_shouldThrowNoSuchElement_whenTodoNotFound() {
        TodoService todoService = new TodoService(mockTodoRepository);
        UUID todoId = UUID.randomUUID();
        when(mockTodoRepository.findById(todoId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> todoService.getTodo(todoId));
        verify(mockTodoRepository).findById(todoId);
    }

    @Test
    void addTodo() {
        TodoService todoService = new TodoService(mockTodoRepository);
        TodoDTO todoDTO = TodoDTO.builder().status(Status.OPEN).description("todo1").build();

        todoService.addTodo(todoDTO);

        verify(mockTodoRepository).save(captor.capture());

        Todo savedTodo = captor.getValue();

        assertNotNull(savedTodo.id());
        assertEquals("todo1", savedTodo.description());
        assertEquals(Status.OPEN, savedTodo.status());
    }

    @Test
    void updateTodo_shouldUpdateTodo_whenExistingTodoIsFound() {
        TodoService todoService = new TodoService(mockTodoRepository);
        UUID todoId = UUID.randomUUID();
        Todo existingTodo = Todo.builder().status(Status.OPEN).description("todo1").id(todoId).build();
        when(mockTodoRepository.findById(todoId)).thenReturn(Optional.ofNullable(existingTodo));

        TodoDTO todoDTO = TodoDTO.builder().status(Status.IN_PROGRESS).build();

        todoService.updateTodo(todoId, todoDTO);

        verify(mockTodoRepository).findById(todoId);
        verify(mockTodoRepository).save(captor.capture());

        Todo savedTodo = captor.getValue();

        assertNotNull(savedTodo.id());
        assertEquals("todo1", savedTodo.description());
        assertEquals(Status.IN_PROGRESS, savedTodo.status());
    }


    @Test
    void updateTodo_throwNoSuchElementException_whenExistingTodoIsNotFound() {
        TodoService todoService = new TodoService(mockTodoRepository);
        UUID todoId = UUID.randomUUID();
        when(mockTodoRepository.findById(todoId)).thenReturn(Optional.empty());

        TodoDTO todoDTO = TodoDTO.builder().status(Status.IN_PROGRESS).build();

        assertThrows(NoSuchElementException.class, () -> todoService.updateTodo(todoId, todoDTO));

        verify(mockTodoRepository).findById(todoId);
        verifyNoMoreInteractions(mockTodoRepository);
    }

    @Test
    void deleteTodo_deleteTodoById_whenTodoFound() {
        TodoService todoService = new TodoService(mockTodoRepository);
        UUID todoId = UUID.randomUUID();
        Todo existingTodo = Todo.builder().status(Status.OPEN).description("todo1").id(todoId).build();
        when(mockTodoRepository.findById(todoId)).thenReturn(Optional.ofNullable(existingTodo));

        todoService.deleteTodo(todoId);

        verify(mockTodoRepository).findById(todoId);
        verify(mockTodoRepository).deleteById(todoId);
    }

    @Test
    void deleteTodo_shouldThrowNoSuchElementException_whenTodoNotFound() {
        TodoService todoService = new TodoService(mockTodoRepository);
        UUID todoId = UUID.randomUUID();
        when(mockTodoRepository.findById(todoId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> todoService.deleteTodo(todoId));

        verify(mockTodoRepository).findById(todoId);
        verifyNoMoreInteractions(mockTodoRepository);
    }
}