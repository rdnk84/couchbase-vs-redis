package com.example.couchbaseVSRedisProject;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(DocumentNotFoundException.class)
    public String documentNotFoundException() {
        return "например возвращаем клиенту страницу с информацией";
    }
}
