package br.com.estudos.api.controllers;


import br.com.estudos.api.domain.dto.UsersDTO;
import br.com.estudos.api.services.UserService;
import br.com.estudos.api.services.impl.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserServiceImpl userService;

    @PostMapping
    public ResponseEntity<UsersDTO> createUsers(@RequestBody UsersDTO usersDTO) {
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}").buildAndExpand(userService.createUser(usersDTO).getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UsersDTO> findByUserId(@PathVariable Integer id) {
        return ResponseEntity.ok().body(modelMapper.map(userService.findById(id), UsersDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<UsersDTO>> findAllUsers() {
        return ResponseEntity.ok()
                .body(userService.findAll().stream().map(users -> modelMapper.map(users, UsersDTO.class)).collect(Collectors.toList()));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UsersDTO> updateUser(@PathVariable Integer id, @RequestBody UsersDTO usersDTO) {
        usersDTO.setId(id);
        return ResponseEntity.ok().body(modelMapper.map(userService.updateUsers(usersDTO), UsersDTO.class));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UsersDTO> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}


