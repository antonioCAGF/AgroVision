package br.com.agrovision.service;


import br.com.agrovision.model.Donation;
import br.com.agrovision.model.User;

import java.util.List;

public interface UserService {
    void registerUser(User user) throws IllegalArgumentException;

    User getUserById(Long userId);

    User getUserByEmail(String email);

    boolean isUserValid(Long id);

    void createDonation(Long userId, Donation donation);

    void createAdminUser(User user);

    List<Donation> listDonationsForAdmin();

}
