package br.com.estudos.api.services;

import br.com.estudos.api.domain.Users;

public interface UserService {

    Users findById(Integer id);
}
