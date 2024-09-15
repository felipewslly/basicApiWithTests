package br.com.estudos.api.controllers;

import br.com.estudos.api.domain.Users;
import br.com.estudos.api.domain.dto.UsersDTO;
import br.com.estudos.api.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;


@SpringBootTest
class UserControllerTest {

    public static final Integer ID = 1;
    public static final String NAME = "felipe";
    public static final String EMAIL = "felipe@gmail.com";
    public static final String PASSWORD = "1234";

    private Users users;
    private UsersDTO usersDTO;

    @InjectMocks
    private UserController userController;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserServiceImpl userService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUsers();
    }

    @Test
    void whenCreateUsersThenReturnCreated() {
        Mockito.when(userService.createUser(Mockito.any())).thenReturn(users);

        ResponseEntity<UsersDTO> response = userController.createUsers(usersDTO);

        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getHeaders().get("Location"));



    }

    @Test
    void whenfindByUserIdThenReturnSucess() {
        Mockito.when(userService.findById(Mockito.anyInt())).thenReturn(users);
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(usersDTO);

        ResponseEntity<UsersDTO> response = userController.findByUserId(ID);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());

        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(UsersDTO.class, response.getBody().getClass());

        Assertions.assertEquals(ID, response.getBody().getId());
        Assertions.assertEquals(NAME, response.getBody().getName());
        Assertions.assertEquals(EMAIL, response.getBody().getEmail());
        Assertions.assertEquals(PASSWORD, response.getBody().getPassword());

    }

    @Test
    void whenfindAllUsersReturnAnListOfDTOs() {
        Mockito.when(userService.findAll()).thenReturn(List.of(users));
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(usersDTO);

        ResponseEntity<List<UsersDTO>> response = userController.findAllUsers();

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertTrue(response.getBody() instanceof List);
        Assertions.assertEquals(UsersDTO.class, response.getBody().get(0).getClass());
        Assertions.assertEquals(ID, response.getBody().get(0).getId());
        Assertions.assertEquals(NAME, response.getBody().get(0).getName());
        Assertions.assertEquals(EMAIL, response.getBody().get(0).getEmail());
        Assertions.assertEquals(PASSWORD, response.getBody().get(0).getPassword());


    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    private void startUsers() {
        users = new Users(ID, NAME, EMAIL, PASSWORD);
        usersDTO = new UsersDTO(ID, NAME, EMAIL, PASSWORD);
    }
}