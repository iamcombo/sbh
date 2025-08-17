package com.example.springboothomework.service;

import com.example.springboothomework.entity.Todo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TodoService {
    ResponseEntity<List<Todo>> findAll();
    ResponseEntity<Todo> findById(Long id);
    ResponseEntity<Todo> save(Todo todo);
    ResponseEntity<Todo> update(Long id, Todo todo);
    void deleteById(Long id);
}
