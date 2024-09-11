package br.com.estudos.api.controllers;


import br.com.estudos.api.config.ModelMapperConfig;
import br.com.estudos.api.domain.Users;
import br.com.estudos.api.domain.dto.UsersDTO;
import br.com.estudos.api.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> findByUserId(@PathVariable Integer id){
            return ResponseEntity.ok().body(modelMapper.map(userService.findById(id), UsersDTO.class));
    }
}
