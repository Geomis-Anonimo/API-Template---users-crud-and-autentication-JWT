package com.finances.finance_control.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class CPF implements Serializable {

    @Column(name = "cpf", nullable = false, unique = true)
    private String number;

    public CPF(String number) {
        if (!isValid(number)) {
            throw new IllegalArgumentException("CPF inválido: " + number);
        }
        this.number = clean(number);
    }

    public static boolean isValid(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return false;
        }

        cpf = clean(cpf);

        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        int digito1 = calculateDigit(cpf.substring(0, 9));
        if (digito1 != Character.getNumericValue(cpf.charAt(9))) {
            return false;
        }

        int digito2 = calculateDigit(cpf.substring(0, 10));
        return digito2 == Character.getNumericValue(cpf.charAt(10));
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

    public String getFormatado() {
        return number.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    @Override
    public String toString() {
        return getFormatado();
    }
}