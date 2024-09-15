package br.com.estudos.api.controllers.exceptions;

import br.com.estudos.api.services.exceptions.DataIntegrationViolationException;
import br.com.estudos.api.services.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ResourcerExceptionHandlerTest {


    @InjectMocks
    private ResourcerExceptionHandler resourcerExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void whenUserNotFoundThenReturnResponseEntityError() {
        ResponseEntity<StandardError> response = resourcerExceptionHandler
                .userNotFound(new UserNotFoundException("User not found"),
                        new MockHttpServletRequest());

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(StandardError.class, response.getBody().getClass());
        Assertions.assertEquals("User not found", response.getBody().getError());
        Assertions.assertEquals(404, response.getBody().getStatus());


    }

    @Test
    void dataIntegrationViolation() {
        ResponseEntity<StandardError> response = resourcerExceptionHandler
                .dataIntegrationViolation(new DataIntegrationViolationException("Email já cadastrado"),
                        new MockHttpServletRequest());

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(StandardError.class, response.getBody().getClass());
        Assertions.assertEquals("Email já cadastrado", response.getBody().getError());
        Assertions.assertEquals(400 , response.getBody().getStatus());
    }
}