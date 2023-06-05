package br.com.agrovision.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {

    @Id
    private Long id;
    private String fullName;
    private String address;
    private String email;
    private String password;
    private String phoneNumber;
    private String additionalInfo;
    private List<Donation> donations;
    private boolean isAdmin;
    public User() {
        this.donations = new ArrayList<>();
    }
    public boolean isValid() {
        return false;
    }
}
