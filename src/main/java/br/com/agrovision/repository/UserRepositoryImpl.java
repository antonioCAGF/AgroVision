package br.com.agrovision.repository;

import br.com.agrovision.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findByEmail(String email) {
        String query = "SELECT * FROM tb_users WHERE email = ?";
        List<User> users = jdbcTemplate.query(query, new Object[]{email}, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setFullName(rs.getString("full_name"));
            user.setAddress(rs.getString("address"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setPhoneNumber(rs.getString("phone_number"));
            user.setAdditionalInfo(rs.getString("additional_info"));
            return user;
        });

        if (users.isEmpty()) {
            return null; // Ou lançar uma exceção apropriada, como EntityNotFoundException
        } else {
            return users.get(0);
        }
    }


    @Override
    public void save(User user) {
        String query = "INSERT INTO tb_users (full_name, address, email, password, phone_number, additional_info) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(query,
                user.getFullName(),
                user.getAddress(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber(),
                user.getAdditionalInfo());
    }
}
