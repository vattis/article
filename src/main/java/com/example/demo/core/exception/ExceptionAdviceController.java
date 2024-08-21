package com.example.demo.core.exception;

import com.example.demo.article.domain.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
public class ExceptionAdviceController{
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity argumentHandle(MethodArgumentTypeMismatchException e){
        log.info("부적절한 argument 감지");
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
