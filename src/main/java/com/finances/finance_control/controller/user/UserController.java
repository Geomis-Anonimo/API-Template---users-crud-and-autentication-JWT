package com.finances.finance_control.controller.user;

import com.finances.finance_control.dto.user.UpdateUserRequestDTO;
import com.finances.finance_control.dto.user.UserMapper;
import com.finances.finance_control.dto.user.UserRequestDTO;
import com.finances.finance_control.dto.user.UserResponseDTO;
import com.finances.finance_control.entity.user.User;
import com.finances.finance_control.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/find-all")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        System.out.println("Teste");
        List<UserResponseDTO> responseDTO = userService.findAll();
        if (responseDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO userResponse = userService.findById(id);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserRequestDTO updateRequest) {

        UserResponseDTO updatedUser = userService.update(id, updateRequest);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
