package com.finances.finance_control.entity.user;

import com.finances.finance_control.infra.exception.CustomException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.regex.Pattern;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class Email implements Serializable {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Column(name = "email", nullable = false, unique = true)
    private String emailAddress;

    public Email(String emailAddress) {
        if (!isValid(emailAddress)) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "Email inválido: " + emailAddress);
        }
        this.emailAddress = emailAddress.toLowerCase().trim();
    }

    public static boolean isValid(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        String emailClean = email.trim().toLowerCase();

        if (!EMAIL_PATTERN.matcher(emailClean).matches()) {
            return false;
        }

        if (emailClean.length() > 255) {
            return false;
        }

        String localPart = emailClean.split("@")[0];
        return !localPart.isEmpty();
    }

    public String getLocalPart() {
        return emailAddress.split("@")[0];
    }

    public String getDomain() {
        return emailAddress.split("@")[1];
    }

    @Override
    public String toString() {
        return emailAddress;
    }
}