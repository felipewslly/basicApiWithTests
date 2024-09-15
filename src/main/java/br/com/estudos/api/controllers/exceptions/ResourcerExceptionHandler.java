package br.com.estudos.api.controllers.exceptions;


import br.com.estudos.api.services.exceptions.DataIntegrationViolationException;
import br.com.estudos.api.services.exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ResourcerExceptionHandler{

    @ExceptionHandler(UserNotFoundException.class)
     public ResponseEntity<StandardError> userNotFound(UserNotFoundException userNotFoundException, HttpServletRequest request){
            StandardError standardError = new StandardError(LocalDateTime.now(),
                    HttpStatus.NOT_FOUND.value(),
                    userNotFoundException.getMessage(),
                    request.getRequestURI());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
     }

     @ExceptionHandler(DataIntegrationViolationException.class)
     public ResponseEntity<StandardError> dataIntegrationViolation(DataIntegrationViolationException dataIntegrationViolationException, HttpServletRequest request){
            StandardError dataIntegrationException = new StandardError(LocalDateTime.now(),
                    HttpStatus.BAD_REQUEST.value(),
                    dataIntegrationViolationException.getMessage(),
                    request.getRequestURI());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataIntegrationException);
     }
}
