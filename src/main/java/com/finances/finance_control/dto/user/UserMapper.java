package com.finances.finance_control.dto.user;

import com.finances.finance_control.entity.user.CPF;
import com.finances.finance_control.entity.user.Email;
import com.finances.finance_control.entity.user.User;

public class UserMapper {

    public static UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail().getEmailAddress());
        return dto;
    }

    public static User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setEmail(new Email(dto.getEmail()));
        user.setPassword(dto.getPassword());
        user.setCpf(new CPF(dto.getCpf()));

        return user;
    }
}