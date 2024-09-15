package br.com.estudos.api.services;

import br.com.estudos.api.domain.Users;
import br.com.estudos.api.domain.dto.UsersDTO;

import java.util.List;

public interface UserService {

    Users findById(Integer id);

    List<Users> findAll();

    Users createUser(UsersDTO dto);

    Users updateUsers(UsersDTO usersDTO);

    void deleteUser(Integer id);
}
