package com.finances.finance_control.dto.user;

import com.finances.finance_control.entity.user.UserRole;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String name;
    private String username;
    private String email;
    private UserRole role;
}
