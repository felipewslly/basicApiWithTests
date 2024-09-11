package br.com.estudos.api.services.impl;

import br.com.estudos.api.domain.Users;
import br.com.estudos.api.repositories.UserRepository;
import br.com.estudos.api.services.UserService;
import br.com.estudos.api.services.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public Users findById(Integer id) {
        Optional<Users> userById = userRepository.findById(id);
        return userById.orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
