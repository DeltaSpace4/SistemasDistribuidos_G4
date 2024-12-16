package com.mycompany.crivosegmentado;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CrivoSegmentado {
    public static void main(String[] args) {
        int totalPrimos = 0;
        int numThreads = 4;
        Scanner scanner = new Scanner(System.in);

        // Entrada do intervalo
        System.out.print("Digite o início do intervalo: ");
        int inicio = scanner.nextInt();

        System.out.print("Digite o fim do intervalo: ");
        int fim = scanner.nextInt();

        // Validação do intervalo
        if (inicio > fim) {
            System.out.println("O início do intervalo não pode ser maior que o fim.");
            return;
        }

        // Capturar o tempo inicial usando nanoTime()
        long inicioExec = System.nanoTime();

        // Encontrar primos no intervalo usando o Crivo Segmentado
        List<Integer> primos = crivoSegmentado(inicio, fim);

        // Capturar o tempo final usando nanoTime()
        long fimExec = System.nanoTime();

        // Calcular e exibir o tempo de execução
        long tempoExec = fimExec - inicioExec; // Tempo em milissegundos
        System.out.println("Tempo de execução: " + tempoExec / 1000000.0 + " ms");

        // Exibir os números primos encontrados
        System.out.println("Números primos no intervalo [" + inicio + ", " + fim + "]:");
        for (int primo : primos) {
            totalPrimos += 1;
        }
        System.out.print("Total de primos: " + totalPrimos);
    }

    // Função para encontrar primos usando o Crivo Segmentado
    public static List<Integer> crivoSegmentado(int inicio, int fim) {
        // Passo 1: Calcular todos os primos até sqrt(fim) usando o Crivo de Eratóstenes
        int limite = (int) Math.sqrt(fim);
        List<Integer> primosBase = crivoEratostenes(limite);

        // Passo 2: Criar um array booleano para marcar números compostos no intervalo
        boolean[] isComposto = new boolean[fim - inicio + 1];

        // Passo 3: Marcar múltiplos de cada primo no intervalo
        for (int primo : primosBase) {
            // Encontrar o menor múltiplo do primo dentro do intervalo
            int menorMultiplo = Math.max(primo * primo, (inicio + primo - 1) / primo * primo);

            // Marcar todos os múltiplos do primo como compostos
            for (int j = menorMultiplo; j <= fim; j += primo) {
                isComposto[j - inicio] = true;
            }
        }

        // Passo 4: Coletar os números primos do intervalo
        List<Integer> primos = new ArrayList<>();
        for (int i = 0; i < isComposto.length; i++) {
            if (!isComposto[i]) {
                int numero = i + inicio;
                if (numero > 1) { // Ignorar 1, que não é primo
                    primos.add(numero);
                }
            }
        }
        return primos;
    }

    // Função auxiliar: Crivo de Eratóstenes para encontrar primos até um limite
    public static List<Integer> crivoEratostenes(int limite) {
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