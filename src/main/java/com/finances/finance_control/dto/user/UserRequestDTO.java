package com.finances.finance_control.dto.user;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String name;
    private String username;
    private String email;
    private String password;
    private String cpf;
}
