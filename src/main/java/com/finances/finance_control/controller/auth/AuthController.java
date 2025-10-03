package com.finances.finance_control.controller.auth;

import com.finances.finance_control.dto.auth.AuthResponseDTO;
import com.finances.finance_control.dto.auth.LoginRequest;
import com.finances.finance_control.dto.auth.JwtResponse;
import com.finances.finance_control.infra.exception.CustomException;
import com.finances.finance_control.service.auth.AuthService;
import com.finances.finance_control.service.auth.TokenJwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private TokenJwtService tokenService;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthResponseDTO dataResponse = authService.authenticate(
                loginRequest.getEmailOrUsername(),
                loginRequest.getPassword()
        );

        if (!dataResponse.isStatus()) {
            throw new CustomException(401, "E-mail ou senha incorretos");
        }

        String jwt = tokenService.generateToken(dataResponse.getEmail());
        return ResponseEntity.ok(new JwtResponse("Bearer", jwt, jwtExpiration));
    }
}
