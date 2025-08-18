package com.example.springboothomework.service.impl;

import com.example.springboothomework.controller.request.TodoCreateRequest;
import com.example.springboothomework.controller.request.TodoUpdateRequest;
import com.example.springboothomework.entity.Todo;
import com.example.springboothomework.exception.TodoNotFoundException;
import com.example.springboothomework.repository.TodoRepository;
import com.example.springboothomework.service.TodoService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;
    
    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public ResponseEntity<List<Todo>> findAll() {
        return ResponseEntity.ok().body(todoRepository.findAll());
    }

    @Override
    public ResponseEntity<Todo> findById(Long id) {
        Optional<Todo> todo = todoRepository.findById(id);

        if (todo.isPresent()) {
            return ResponseEntity.ok(todo.get());
        } else {
            throw new TodoNotFoundException(id, "/api/todos/" + id);
        }
    }

    @Override
    public ResponseEntity<Todo> save(TodoCreateRequest body) {
        final var result = todoRepository.save(body.toTodoEntity());
        return ResponseEntity.status(201).body(result);
    }

    @Override
    @Transactional
    public ResponseEntity<Todo> update(Long id, TodoUpdateRequest body) {
        Optional<Todo> existingTodo = todoRepository.findById(id);

        if (existingTodo.isPresent()) {
            Todo updated = existingTodo.get().updateCompleted(body);
            return ResponseEntity.ok(todoRepository.save(updated));
        } else {
            throw new TodoNotFoundException(id, "/api/todos/" + id);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Todo> deleteById(Long id) {
        todoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
