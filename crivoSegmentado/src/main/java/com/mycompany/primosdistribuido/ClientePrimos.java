
package com.mycompany.primosdistribuido;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientePrimos {

    private static final String HOST = "localhost";
    private static final int PORTA = 58000;

    public static void main(String[] args) {
        try (Socket conexao = new Socket(HOST, PORTA);
             ObjectInputStream entrada = new ObjectInputStream(conexao.getInputStream());
             ObjectOutputStream saida = new ObjectOutputStream(conexao.getOutputStream())) {

            // Receber subintervalo do servidor
            long inicio = entrada.readLong();
            long fim = entrada.readLong();
            System.out.println("Processando intervalo [" + inicio + ", " + fim + "]");

            // Calcular primos no intervalo
            long primosEncontrados = contarPrimos(inicio, fim);

            // Enviar resultado ao servidor
            saida.writeLong(primosEncontrados);
            System.out.println("Resultado enviado ao servidor: " + primosEncontrados);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long contarPrimos(long inicio, long fim) {
        long count = 0;
        boolean[] isComposto = new boolean[(int) (fim - inicio + 1)];
        int limite = (int) Math.sqrt(fim);

        List<Integer> primosBase = calcularPrimosBase(limite);

        for (int primo : primosBase) {
            long menorMultiplo = Math.max(primo * primo, (inicio + primo - 1) / primo * primo);
            for (long j = menorMultiplo; j <= fim; j += primo) {
                isComposto[(int) (j - inicio)] = true;
            }
        }

        for (int i = 0; i < isComposto.length; i++) {
            if (!isComposto[i] && (i + inicio) > 1) {
                count++;
            }
        }

        return count;
    }

    private static List<Integer> calcularPrimosBase(int limite) {
        boolean[] isComposto = new boolean[limite + 1];
        List<Integer> primos = new ArrayList<>();

        for (int i = 2; i <= limite; i++) {
            if (!isComposto[i]) {
                primos.add(i);
                for (int j = i * i; j <= limite; j += i) {
                    isComposto[j] = true;
                }
            }
        }

        return primos;
    }
}
