package com.finances.finance_control.dto.user;

import com.finances.finance_control.entity.user.UserRole;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String name;
    private String username;
    private String email;
    private String password;
    private String cpf;
    private UserRole role = UserRole.USER;
}
