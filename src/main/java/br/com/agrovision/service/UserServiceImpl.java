package br.com.agrovision.service;

import br.com.agrovision.model.User;
import br.com.agrovision.repository.UserRepository;
import br.com.agrovision.util.PasswordEncoderUtil;
import org.springframework.stereotype.Service;

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
}
