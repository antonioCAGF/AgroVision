package br.com.agrovision.service;

import br.com.agrovision.model.Donation;
import br.com.agrovision.model.User;
import br.com.agrovision.repository.UserRepository;
import br.com.agrovision.util.PasswordEncoderUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(User user) throws IllegalArgumentException {
        validateUser(user);
        encryptPassword(user);
        checkEmailAvailability(user.getEmail());
        userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public boolean isUserValid(Long id) {
        return false;
    }

    private void validateUser(User user) throws IllegalArgumentException {
        if (user.getFullName() == null || user.getFullName().isEmpty()) {
            throw new IllegalArgumentException("O campo 'full_name' é obrigatório.");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("O campo 'email' é obrigatório.");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("O campo 'password' é obrigatório.");
        }
        // Adicione as validações necessárias para os demais campos
    }

    private void encryptPassword(User user) {
        String encryptedPassword = PasswordEncoderUtil.encode(user.getPassword());
        user.setPassword(encryptedPassword);
    }

    private void checkEmailAvailability(String email) throws IllegalArgumentException {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser != null) {
            throw new IllegalArgumentException("O e-mail fornecido já está em uso.");
        }
    }
    @Override
    public void createDonation(Long userId, Donation donation) {
        User user = userRepository.findById(userId);
        if (user != null) {
            user.getDonations().add(donation);
            userRepository.saveDonation(userId, donation);
        } else {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
    }
    @Override
    public void createAdminUser(User user) {
        user.setAdmin(true);
        registerUser(user);
    }

    @Override
    public List<Donation> listDonationsForAdmin() {
        List<User> adminUsers = userRepository.findAllAdminUsers();
        if (!adminUsers.isEmpty()) {
            User adminUser = adminUsers.get(0);
            return adminUser.getDonations();
        } else {
            throw new IllegalArgumentException("Usuário administrador não encontrado.");
        }
    }

}
