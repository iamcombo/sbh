package com.example.springboothomework.service;

import com.example.springboothomework.controller.request.TodoCreateRequest;
import com.example.springboothomework.controller.request.TodoUpdateRequest;
import com.example.springboothomework.entity.Todo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TodoService {
    ResponseEntity<List<Todo>> findAll();
    ResponseEntity<Todo> findById(Long id);
    ResponseEntity<Todo> save(TodoCreateRequest body);
    ResponseEntity<Todo> update(Long id, TodoUpdateRequest body);
    ResponseEntity<Todo> deleteById(Long id);
}
