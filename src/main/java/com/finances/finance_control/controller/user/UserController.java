package com.finances.finance_control.controller.user;

import com.finances.finance_control.dto.user.UserMapper;
import com.finances.finance_control.dto.user.UserRequestDTO;
import com.finances.finance_control.dto.user.UserResponseDTO;
import com.finances.finance_control.entity.user.User;
import com.finances.finance_control.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        User newUser = userService.create(user);
        UserResponseDTO responseDTO = UserMapper.toResponseDTO(newUser);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}
