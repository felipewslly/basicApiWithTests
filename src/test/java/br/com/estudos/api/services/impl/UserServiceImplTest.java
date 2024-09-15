package br.com.estudos.api.services.impl;

import br.com.estudos.api.config.ModelMapperConfig;
import br.com.estudos.api.domain.Users;
import br.com.estudos.api.domain.dto.UsersDTO;
import br.com.estudos.api.repositories.UserRepository;
import br.com.estudos.api.services.exceptions.DataIntegrationViolationException;
import br.com.estudos.api.services.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    public static final Integer ID = 1;
    public static final String NAME = "felipe";
    public static final String EMAIL = "felipe@gmail.com";
    public static final String PASSWORD = "1234";
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    private Users users;
    private UsersDTO usersDTO;
    private Optional<Users> usersOptional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUsers();
    }

    @Test
    void whenFindByIdThenReturnAnUserInstance() {
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(usersOptional);

        Users response = userService.findById(ID);


        Assertions.assertNotNull(response);
        Assertions.assertEquals(Users.class, response.getClass());
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(NAME, response.getName());
        Assertions.assertEquals(EMAIL, response.getEmail());


    }

    @Test
    void whenFindByIdThenReturnAnUserNotFound() {
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenThrow(new UserNotFoundException("User not found"));

        try {
            userService.findById(ID);
        } catch (Exception e) {
            Assertions.assertEquals(UserNotFoundException.class, e.getClass());
            Assertions.assertEquals("User not found", e.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(List.of(users));

        List<Users> response = userService.findAll();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals(Users.class, response.get(0).getClass());
        Assertions.assertEquals(ID, response.get(0).getId());
        Assertions.assertEquals(NAME, response.get(0).getName());
        Assertions.assertEquals(EMAIL, response.get(0).getEmail());


    }

    @Test
    void whenCreateThenReturnSucess() {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(users);

        Users response = userService.createUser(usersDTO);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Users.class, response.getClass());
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(NAME, response.getName());
        Assertions.assertEquals(EMAIL, response.getEmail());
        Assertions.assertEquals(PASSWORD, response.getPassword());

    }

    @Test
    void whenUpdateThenReturnSucess() {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(users);

        Users response = userService.updateUsers(usersDTO);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Users.class, response.getClass());
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(NAME, response.getName());
        Assertions.assertEquals(EMAIL, response.getEmail());
        Assertions.assertEquals(PASSWORD, response.getPassword());

    }

    @Test
    void whenCreateThenReturnAnDataIntegrationViolationException() {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(usersOptional);

        try {
            usersOptional.get().setId(2);
            userService.createUser(usersDTO);
        } catch (Exception e) {
            Assertions.assertEquals(DataIntegrationViolationException.class, e.getClass());
            Assertions.assertEquals("Este email já está cadastrado", e.getMessage());
        }
    }

    @Test
    void whenUpdateThenReturnAnDataIntegrationViolationException() {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(usersOptional);

        try {
            usersOptional.get().setId(2);
            userService.updateUsers(usersDTO);
        } catch (Exception e) {
            Assertions.assertEquals(DataIntegrationViolationException.class, e.getClass());
            Assertions.assertEquals("Este email já está cadastrado", e.getMessage());
        }
    }
    @Test
    void deleteWithUserNotFoundException(){
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenThrow(new UserNotFoundException("User not found"));
        try{
            userService.deleteUser(ID);
        }catch (Exception e){
            Assertions.assertEquals(UserNotFoundException.class, e.getClass());
            Assertions.assertEquals("User not found", e.getMessage());
        }
    }

    @Test
    void deleteUserWithSucess() {
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(usersOptional);
        Mockito.doNothing().when(userRepository).deleteById(Mockito.anyInt());
        userService.deleteUser(ID);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(Mockito.anyInt() );
    }

    private void startUsers() {
        users = new Users(ID, NAME, EMAIL, PASSWORD);
        usersDTO = new UsersDTO(ID, NAME, EMAIL, PASSWORD);
        usersOptional = Optional.of(users);
    }
}