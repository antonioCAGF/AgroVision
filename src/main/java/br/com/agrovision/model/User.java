package br.com.agrovision.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
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

    public boolean isValid() {
        return false;
    }
}
