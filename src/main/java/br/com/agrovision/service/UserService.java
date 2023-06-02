package br.com.agrovision.service;


import br.com.agrovision.model.User;

public interface UserService {
    void registerUser(User user) throws IllegalArgumentException;
}
