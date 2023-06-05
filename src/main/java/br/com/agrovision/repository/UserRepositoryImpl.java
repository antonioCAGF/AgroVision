package br.com.agrovision.repository;

import br.com.agrovision.model.Donation;
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
        String query = "INSERT INTO tb_users (full_name, address, email, password, phone_number, additional_info, is_admin) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(query,
                user.getFullName(),
                user.getAddress(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber(),
                user.getAdditionalInfo(),
                user.isAdmin());
    }

    @Override
    public List<User> findAllAdminUsers() {
        String query = "SELECT * FROM tb_users WHERE is_admin = true";
        return jdbcTemplate.query(query, (rs, rowNum) -> mapUser(rs));
    }

    @Override
    public User findById(Long id) {
        String query = "SELECT * FROM tb_users WHERE user_id_PK = ?";
        List<User> users = jdbcTemplate.query(query, new Object[]{id}, (rs, rowNum) -> mapUser(rs));
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public boolean existsById(Long id) {
        String query = "SELECT COUNT(*) FROM tb_users WHERE user_id_PK = ?";
        Integer count = jdbcTemplate.queryForObject(query, new Object[]{id}, Integer.class);
        return count != null && count > 0;
    }

    @Override
    public void saveDonation(Long userId, Donation donation) {
        String query = "INSERT INTO tb_donations (user_id_FK, donation_type, donation_description, donation_datetime, donation_status) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(query,
                userId,
                donation.getDonationType(),
                donation.getDonationDescription(),
                donation.getDonationDatetime(),
                donation.getDonationStatus());
    }

    private User mapUser(ResultSet rs) {
        User user = new User();
        try {
            user.setId(rs.getLong("user_id_PK"));
            user.setFullName(rs.getString("full_name"));
            user.setAddress(rs.getString("address"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setPhoneNumber(rs.getString("phone_number"));
            user.setAdditionalInfo(rs.getString("additional_info"));

            // Recupera as doações do usuário
            Long userId = user.getId();
            List<Donation> donations = getDonationsByUserId(userId);
            user.setDonations(donations);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    private List<Donation> getDonationsByUserId(Long userId) {
        String query = "SELECT * FROM tb_donations WHERE user_id_FK = ?";
        return jdbcTemplate.query(query, new Object[]{userId}, (rs, rowNum) -> mapDonation(rs));
    }

    private Donation mapDonation(ResultSet rs) {
        Donation donation = new Donation();
        try {
            donation.setUserId(rs.getLong("user_id_FK"));
            donation.setDonationId(rs.getLong("donation_id_PK"));
            donation.setDonationType(rs.getString("donation_type"));
            donation.setDonationDescription(rs.getString("donation_description"));
            donation.setDonationDatetime(rs.getString("donation_datetime"));
            donation.setDonationStatus(rs.getString("donation_status"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return donation;
    }

}
