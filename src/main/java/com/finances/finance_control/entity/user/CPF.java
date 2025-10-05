package com.finances.finance_control.entity.user;

import com.finances.finance_control.infra.exception.CustomException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class CPF implements Serializable {

    @Column(name = "cpf", nullable = false, unique = true)
    private String number;

    public CPF(String number) {
        String cpf = clean(number);
        System.out.println("CPF: " + cpf);

        if (!isValid(cpf)) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "CPF inválido " + number);
        }
        this.number = formatCpf(cpf);
    }

    public static boolean isValid(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return false;
        }

        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        int digit1 = calculateDigit(cpf.substring(0, 9));
        if (digit1 != Character.getNumericValue(cpf.charAt(9))) {
            return false;
        }

        int digit2 = calculateDigit(cpf.substring(0, 10));
        return digit2 == Character.getNumericValue(cpf.charAt(10));
    }

    private static int calculateDigit(String cpfPartial) {
        int weight = cpfPartial.length() + 1;
        int sum = 0;

        for (int i = 0; i < cpfPartial.length(); i++) {
            sum += Character.getNumericValue(cpfPartial.charAt(i)) * weight;
            weight--;
        }

        int remainder = sum % 11;
        return (remainder < 2) ? 0 : (11 - remainder);
    }

    private static String clean(String cpf) {
        return cpf.replaceAll("\\D", "");
    }

    private static String formatCpf(String cleanCpf) {
        return cleanCpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    @Override
    public String toString() {
        return this.number;
    }
}