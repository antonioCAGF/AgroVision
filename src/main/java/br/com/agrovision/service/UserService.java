package br.com.agrovision.service;


import br.com.agrovision.model.User;

public interface UserService {
    void registerUser(User user) throws IllegalArgumentException;
    User getUserById(Long userId);
    User getUserByEmail(String email);
    boolean isUserValid(Long id);
}
