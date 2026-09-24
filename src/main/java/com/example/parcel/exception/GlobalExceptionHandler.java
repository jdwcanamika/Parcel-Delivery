package com.example.parcel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ParcelNotFoundException.class)
    ResponseEntity<?> notFound(ParcelNotFoundException e){ return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error(404,e.getMessage())); }
    @ExceptionHandler(InvalidStatusException.class)
    ResponseEntity<?> badStatus(InvalidStatusException e){ return ResponseEntity.badRequest().body(error(400,e.getMessage())); }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> validation(MethodArgumentNotValidException e){
        String msg=e.getBindingResult().getFieldErrors().stream().findFirst().map(x->x.getField()+": "+x.getDefaultMessage()).orElse("Invalid request");
        return ResponseEntity.badRequest().body(error(400,msg));
    }
    private Map<String,Object> error(int status,String message){return Map.of("timestamp", LocalDateTime.now(),"status",status,"message",message);}
}
