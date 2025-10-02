package com.finances.finance_control.service.user;

import com.finances.finance_control.dto.user.UpdateUserRequestDTO;
import com.finances.finance_control.dto.user.UserMapper;
import com.finances.finance_control.dto.user.UserResponseDTO;
import com.finances.finance_control.entity.user.CPF;
import com.finances.finance_control.entity.user.Email;
import com.finances.finance_control.entity.user.User;
import com.finances.finance_control.infra.exception.CustomException;
import com.finances.finance_control.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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

        Optional<User> cpfAlreadyExists = userRepository.findByCpf(user.getCpf());
        if (cpfAlreadyExists.isPresent()) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "CPF já cadastrado");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<UserResponseDTO> findAll() {
        List<User> users = userRepository.findAll();
        List<UserResponseDTO> usersResponseDTO = new ArrayList<>();

        if (users.isEmpty()) {
            return usersResponseDTO;
        }

        for (User user : users) {
            UserResponseDTO userResponseDTO = UserMapper.toResponseDTO(user);
            usersResponseDTO.add(userResponseDTO);
        }

        return usersResponseDTO;
    }

//    public List<UserResponseDTO> findAll() {
//        return userRepository.findAll()
//                .stream()
//                .map(UserMapper::toResponseDTO)
//                .collect(Collectors.toList());
//    }

    public UserResponseDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(), "Usuário com id: " + id + " não encontrado"));

        return UserMapper.toResponseDTO(user);
    }

    public UserResponseDTO update(Long id, UpdateUserRequestDTO updateRequest) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(), "Usuário com id: " + id + " não encontrado"));

        if (updateRequest.getName() != null) {
            existingUser.setName(updateRequest.getName());
        }

        if (updateRequest.getUsername() != null) {
            if (!existingUser.getUsername().equals(updateRequest.getUsername())) {
                userRepository.findByUsername(updateRequest.getUsername())
                        .ifPresent(user -> {
                            throw new IllegalArgumentException("Username already exists");
                        });
            }
            existingUser.setUsername(updateRequest.getUsername());
        }

        if (updateRequest.getEmail() != null) {
            userRepository.findByEmail(new Email(updateRequest.getEmail()))
                    .ifPresent(user -> {
                        throw new IllegalArgumentException("Email already exists");
                    });
            existingUser.setEmail(new Email(updateRequest.getEmail()));
        }

        if (updateRequest.getCpf() != null) {
            new CPF(updateRequest.getCpf());
            userRepository.findByCpf(new CPF(updateRequest.getCpf()))
                        .ifPresent(user -> {
                            throw new IllegalArgumentException("CPF already exists");
                        });
            existingUser.setCpf(new CPF(updateRequest.getCpf()));
        }

        User updatedUser = userRepository.save(existingUser);
        return UserMapper.toResponseDTO(updatedUser);
    }
}
