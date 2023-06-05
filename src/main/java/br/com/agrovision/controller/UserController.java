package br.com.agrovision.controller;

import br.com.agrovision.model.Donation;
import br.com.agrovision.model.User;
import br.com.agrovision.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/insert")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Cadastro de Usuários
    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("Usuário cadastrado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/{userId}/donations")
    public ResponseEntity<String> createDonation(@PathVariable Long userId, @RequestBody Donation donation) {
        try {
            userService.createDonation(userId, donation);
            return ResponseEntity.ok("Doação criada com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/admin")
    public ResponseEntity<String> createAdminUser(@RequestBody User user) {
        try {
            userService.createAdminUser(user);
            return ResponseEntity.ok("Usuário administrador criado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/list/donations")
    public ResponseEntity<List<Donation>> listDonationsForAdmin() {
        try {
            List<Donation> donations = userService.listDonationsForAdmin();
            return ResponseEntity.ok(donations);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
