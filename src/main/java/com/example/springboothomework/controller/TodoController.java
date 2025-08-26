package com.example.springboothomework.controller;

import com.example.springboothomework.annotation.AuditFilter;
import com.example.springboothomework.controller.request.TodoCreateRequest;
import com.example.springboothomework.controller.request.TodoUpdateRequest;
import com.example.springboothomework.entity.Todo;
import com.example.springboothomework.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @AuditFilter
    @GetMapping("/list")
    public ResponseEntity<List<Todo>> list() {
        return todoService.findAll();
    }

    @AuditFilter
    @GetMapping("/detail/{id}")
    public ResponseEntity<Todo> detail(@PathVariable("id") Long id) {
        return todoService.findById(id);
    }

    @AuditFilter
    @PostMapping("/create")
    public ResponseEntity<Todo> save(@Valid @RequestBody TodoCreateRequest todo) {
        return todoService.save(todo);
    }

    @AuditFilter
    @PutMapping("/update/{id}")
    public ResponseEntity<Todo> update(@PathVariable Long id, @RequestBody TodoUpdateRequest body) {
        return todoService.update(id, body);
    }

    @AuditFilter
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        todoService.deleteById(id);
    }
}
