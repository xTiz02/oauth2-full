package org.prd.users.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.prd.users.persistence.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalHandlerException {

    //Controla los errores cuando no se encuentra un recurso
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceException(ObjectNotFoundException e, WebRequest webRequest){
        log.error(String.format("Recurso no encontrado en %s: %s",webRequest.getDescription(false), e.getMessage()));
        return new ResponseEntity<>(new ApiResponse(e.getMessage(),LocalDateTime.now(),false), HttpStatus.NOT_FOUND);
    }

    //Controla los errores no esperados
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handlerException(Exception e,
                                                        WebRequest webRequest) {
        log.error(String.format("Error inesperado en %s: %s",webRequest.getDescription(false), e.getMessage()));
        return new ResponseEntity<>(new ApiResponse("Error inesperado", LocalDateTime.now(),false), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}