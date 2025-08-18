package com.example.springboothomework.controller.request;

import com.example.springboothomework.entity.Todo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class TodoCreateRequest {
    @NotBlank
    @Size(max = 100)
    String title;

    @NotBlank
    @Size(max = 500)
    String description;

    public Todo toTodoEntity() {
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setDescription(description);
        return todo;
    }
}