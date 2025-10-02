package com.finances.finance_control.service.user;

import com.finances.finance_control.entity.user.User;
import com.finances.finance_control.infra.exception.CustomException;
import com.finances.finance_control.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User create(User user) {
        Optional<User> emailAlreadyExists = userRepository.findByEmail(user.getEmail());
        if (emailAlreadyExists.isPresent()) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "Email já cadastrado");
        }

        Optional<User> usernameAlreadyExists = userRepository.findByUsername(user.getUsername());
        if (usernameAlreadyExists.isPresent()) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "Nome de usuário já cadastrado");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
