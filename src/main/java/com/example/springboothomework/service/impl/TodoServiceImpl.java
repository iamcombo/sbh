package com.example.springboothomework.service.impl;

import com.example.springboothomework.entity.Todo;
import com.example.springboothomework.repository.TodoRepository;
import com.example.springboothomework.service.TodoService;
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
        List<Todo> todos = todoRepository.findAll();
        return ResponseEntity.ok(todos);
    }

    @Override
    public ResponseEntity<Todo> findById(Long id) {
        Optional<Todo> todo = todoRepository.findById(id);

        if (todo.isPresent()) {
            return ResponseEntity.ok(todo.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Todo> save(Todo todo) {
        Todo saved = todoRepository.save(todo);
        return ResponseEntity.ok(saved);
    }

    @Override
    public ResponseEntity<Todo> update(Long id, Todo todo) {
        Optional<Todo> existingTodo = todoRepository.findById(id);

        if (existingTodo.isPresent()) {
            Todo updated = todoRepository.save(todo);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }
}
