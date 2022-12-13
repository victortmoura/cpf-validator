package com.victormoura;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class CpfValidator {

    private static List<String> invalidCpfList = Arrays
            .asList("00000000000", "11111111111", "22222222222",
                    "33333333333", "44444444444", "55555555555",
                    "66666666666", "77777777777", "88888888888",
                    "99999999999");

	public static boolean isCPF(String cpf) {
        if (cpf == null || invalidCpfList.contains(cpf) || (cpf.length() != 11)) {
            return false;
        }

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            int somatorio = 0;
            int peso = 10;

            char dig10 = getDig10(cpf, somatorio, peso);
            char dig11 = getDig11(cpf);

            // Verifica se os digitos calculados conferem com os digitos informados.
            return conferirDigitosVerificadores(cpf, dig10, dig11);
        } catch (InputMismatchException erro) {
            return false;
        }
    }

    private static boolean conferirDigitosVerificadores(String cpf, char dig10, char dig11) {
        return dig10 == cpf.charAt(9) && dig11 == cpf.charAt(10);
    }

    private static char getDig11(String cpf) {
        int somatorio = 0;
        int peso = 11;

        for(int i=0; i<10; i++) {
            int numero = (cpf.charAt(i) - 48);
            somatorio = somatorio + (numero * peso);
            peso = peso - 1;
        }

        char dig11;
        int resto = 11 - (somatorio % 11);

        if ((resto == 10) || (resto == 11)) {
            dig11 = '0';
        } else {
            dig11 = (char)(resto + 48);
        }

        return dig11;
    }

    private static char getDig10(String cpf, int somatorio, int peso) {
        for (int i = 0; i < 9; i++) {
            // converte o i-esimo caractere do cpf em um numero:
            // por exemplo, transforma o caractere '0' no inteiro 0
            // (48 eh a posicao de '0' na tabela ASCII)
            int numero = (cpf.charAt(i) - 48);
                somatorio += numero * peso;
                peso -= 1;
        }

        char dig10;
        int resto = 11 - (somatorio % 11);
        if ((resto == 10) || (resto == 11)) {
            dig10 = '0';
        } else {
            dig10 = (char)(resto + 48);
        }

        return dig10;
    }

}
