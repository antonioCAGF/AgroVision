package br.com.agrovision.repository;

import br.com.agrovision.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
        List<User> users = jdbcTemplate.query(query, new Object[]{email}, (rs, rowNum) -> mapUser(rs));
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public void save(User user) {
        String query = "INSERT INTO tb_users (full_name, address, email, password, phone_number, additional_info) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(query,
                user.getFullName(),
                user.getAddress(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber(),
                user.getAdditionalInfo());
    }

    @Override
    public User findById(Long id) {
        String query = "SELECT * FROM tb_users WHERE id = ?";
        List<User> users = jdbcTemplate.query(query, new Object[]{id}, (rs, rowNum) -> mapUser(rs));
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public boolean existsById(Long id) {
        String query = "SELECT COUNT(*) FROM tb_users WHERE user_id_PK = ?";
        Integer count = jdbcTemplate.queryForObject(query, new Object[]{id}, Integer.class);
        return count != null && count > 0;
    }

    private User mapUser(ResultSet rs) {
        User user = new User();
        try {
            user.setId(rs.getLong("id"));
            user.setFullName(rs.getString("full_name"));
            user.setAddress(rs.getString("address"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setPhoneNumber(rs.getString("phone_number"));
            user.setAdditionalInfo(rs.getString("additional_info"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
