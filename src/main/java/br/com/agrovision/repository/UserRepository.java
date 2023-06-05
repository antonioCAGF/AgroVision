package br.com.agrovision.repository;

import br.com.agrovision.model.Donation;
import br.com.agrovision.model.User;

import java.util.List;

public interface UserRepository {
    User findByEmail(String email);
    void save(User user);
    User findById(Long id);
    boolean existsById(Long id);
    void saveDonation(Long userId, Donation donation);
    List<User> findAllAdminUsers();
}
