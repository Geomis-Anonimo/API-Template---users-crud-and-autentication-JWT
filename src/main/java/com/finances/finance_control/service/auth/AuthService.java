package com.finances.finance_control.service.auth;

import com.finances.finance_control.dto.auth.AuthResponseDTO;
import com.finances.finance_control.entity.user.User;
import com.finances.finance_control.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponseDTO authenticate(String emailOrUsername, String password) {
        Optional<User> user = userRepo.findByEmailOrUsername(emailOrUsername);

        if (user.isEmpty()) {
            return new AuthResponseDTO(false, null);
        }

        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            return new AuthResponseDTO(false, null);
        }

        return new AuthResponseDTO(true, user.get().getUsername());
    }
}
