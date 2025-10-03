package com.finances.finance_control.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequestDTO {
    private String email;
    private String cpf;

    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;
}