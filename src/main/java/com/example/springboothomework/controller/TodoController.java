package com.example.springboothomework.controller;

import com.example.springboothomework.entity.Todo;
import com.example.springboothomework.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todo")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/list")
    public String list() {
        return todoService.findAll().toString();
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Todo> detail(@PathVariable Long id) {
        return todoService.findById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Todo> save(Todo todo) {
        return todoService.save(todo);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Todo> update(@PathVariable Long id, Todo todo) {
        return todoService.update(id, todo);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        todoService.deleteById(id);
    }
}
