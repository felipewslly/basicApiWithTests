package br.com.estudos.api.services.impl;

import br.com.estudos.api.domain.Users;
import br.com.estudos.api.domain.dto.UsersDTO;
import br.com.estudos.api.repositories.UserRepository;
import br.com.estudos.api.services.UserService;
import br.com.estudos.api.services.exceptions.DataIntegrationViolationException;
import br.com.estudos.api.services.exceptions.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {



    @Autowired
    private  ModelMapper modelMapper;

    @Autowired
    private  UserRepository userRepository;



    @Override
    public Users findById(Integer id) {
        Optional<Users> userById = userRepository.findById(id);
        return userById.orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public List<Users> findAll() {
        return userRepository.findAll();

    }

    @Override
    public Users createUser(UsersDTO dto) {
        findByEmail(dto);
        return userRepository.save(modelMapper.map(dto, Users.class));
    }

    @Override
    public Users updateUsers(UsersDTO usersDTO) {
        findByEmail(usersDTO);
        return userRepository.save(modelMapper.map(usersDTO, Users.class));
    }

    @Override
    public void deleteUser(Integer id) {
        findById(id);
        userRepository.deleteById(id);
    }

    private void findByEmail(UsersDTO usersDTO){
        Optional<Users> userEmail = userRepository.findByEmail(usersDTO.getEmail());
            if (userEmail.isPresent() && !userEmail.get().getId().equals(usersDTO.getId())){
                throw new DataIntegrationViolationException("Este email já está cadastrado");
            }
    }
}
