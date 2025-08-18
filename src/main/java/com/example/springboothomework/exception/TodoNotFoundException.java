package com.example.springboothomework.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TodoNotFoundException extends RuntimeException {
    private final Long todoId;
    private final String path;
    
    public TodoNotFoundException(Long todoId, String path) {
        super("Todo with ID " + todoId + " not found");
        this.todoId = todoId;
        this.path = path;
    }
    
    public TodoNotFoundException(String message, Long todoId, String path) {
        super(message);
        this.todoId = todoId;
        this.path = path;
    }
    
    public Map<String, Object> toMap() {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("timestamp", LocalDateTime.now());
        errorMap.put("status", 404);
        errorMap.put("error", "Not Found");
        errorMap.put("message", getMessage());
        errorMap.put("todoId", todoId);
        errorMap.put("path", path);
        errorMap.put("errorType", "TODO_NOT_FOUND");
        return errorMap;
    }
    
    public Long getTodoId() {
        return todoId;
    }
    
    public String getPath() {
        return path;
    }
}
