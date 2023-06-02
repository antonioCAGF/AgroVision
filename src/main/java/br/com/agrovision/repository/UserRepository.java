package br.com.agrovision.repository;

import br.com.agrovision.model.User;

public interface UserRepository {
    User findByEmail(String email);
    void save(User user);
}
