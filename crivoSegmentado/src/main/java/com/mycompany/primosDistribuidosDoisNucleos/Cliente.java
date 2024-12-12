
package com.mycompany.primosDistribuidosDoisNucleos;


import java.io.*;
import java.net.Socket;

public class Cliente {
    private static final String HOST = "localhost";
    private static final int PORTA = 58000;

    public static void main(String[] args) {
        try (Socket conexao = new Socket(HOST, PORTA);
             ObjectInputStream entrada = new ObjectInputStream(conexao.getInputStream());
             ObjectOutputStream saida = new ObjectOutputStream(conexao.getOutputStream())) {

            // Recebe o intervalo
            long inicio = entrada.readLong();
            long fim = entrada.readLong();
            System.out.println("Processando intervalo: [" + inicio + ", " + fim + "]");

            // Calcula os primos no intervalo
            long primos = calculaPrimos(inicio, fim);

            // Envia o resultado de volta ao servidor
            saida.writeLong(primos);
            saida.flush();
            System.out.println("Resultado enviado ao servidor: " + primos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long calculaPrimos(long inicio, long fim) {
        long contador = 0;
        for (long i = inicio; i <= fim; i++) {
            if (ehPrimo(i)) {
                contador++;
            }
        }
        return contador;
    }

    private static boolean ehPrimo(long numero) {
        if (numero < 2) return false;
        for (long i = 2; i * i <= numero; i++) {
            if (numero % i == 0) return false;
        }
        return true;
    }
}
