package com.mycompany.crivosegmentado;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CrivoSegmentado {
    public static void main(String[] args) {
        int totalPrimos = 0;
        Scanner scanner = new Scanner(System.in);

        // Entrada do intervalo
        System.out.print("Digite o início do intervalo: ");
        long inicio = scanner.nextLong();

        System.out.print("Digite o fim do intervalo: ");
        long fim = scanner.nextLong();

        // Validação do intervalo
        if (inicio > fim) {
            System.out.println("O início do intervalo não pode ser maior que o fim.");
            return;
        }

        // Capturar o tempo inicial usando nanoTime()
        long inicioExec = System.nanoTime();

        // Encontrar primos no intervalo usando o Crivo Segmentado
        List<Integer> primos = crivoSegmentado((int)inicio, (int)fim);  // Cast necessário

        // Capturar o tempo final usando nanoTime()
        long fimExec = System.nanoTime();

        // Calcular e exibir o tempo de execução
        long tempoExec = fimExec - inicioExec;
        System.out.println("Tempo de execução: " + tempoExec / 1000000.0 + " ms");

        // Exibir os números primos encontrados
        System.out.println("Números primos no intervalo [" + inicio + ", " + fim + "]:");
        totalPrimos = primos.size();  // Otimização: usar size() em vez de loop
        System.out.println("Total de primos: " + totalPrimos);
    }

    public static List<Integer> crivoSegmentado(int inicio, int fim) {
        // Otimização: Reduzir o tamanho do segmento para melhor gerenciamento de memória
        int tamanhoSegmento = 1000000;  // Processar em blocos de 1 milhão
        List<Integer> primos = new ArrayList<>();

        // Passo 1: Calcular todos os primos até sqrt(fim) usando o Crivo de Eratóstenes
        int limite = (int) Math.sqrt(fim);
        List<Integer> primosBase = crivoEratostenes(limite);

        // Processar o intervalo em segmentos
        for (int segInicio = inicio; segInicio <= fim; segInicio += tamanhoSegmento) {
            int segFim = Math.min(segInicio + tamanhoSegmento - 1, fim);

            // Passo 2: Criar um array booleano para o segmento atual
            boolean[] isComposto = new boolean[segFim - segInicio + 1];

            // Passo 3: Marcar múltiplos de cada primo no segmento atual
            for (int primo : primosBase) {
                int menorMultiplo = Math.max(primo * primo, ((segInicio + primo - 1) / primo) * primo);
                for (int j = menorMultiplo; j <= segFim; j += primo) {
                    isComposto[j - segInicio] = true;
                }
            }

            // Passo 4: Coletar os números primos do segmento atual
            for (int i = 0; i < isComposto.length; i++) {
                if (!isComposto[i]) {
                    int numero = i + segInicio;
                    if (numero > 1) {
                        primos.add(numero);
                    }
                }
            }
        }
        return primos;
    }

    public static List<Integer> crivoEratostenes(int limite) {
        boolean[] isComposto = new boolean[limite + 1];
        List<Integer> primos = new ArrayList<>();

        // Otimização: Reduzir o limite do loop externo para sqrt(limite)
        for (int i = 2; i * i <= limite; i++) {
            if (!isComposto[i]) {
                for (int j = i * i; j <= limite; j += i) {
                    isComposto[j] = true;
                }
            }
        }

        // Coletar os primos em uma passagem separada
        for (int i = 2; i <= limite; i++) {
            if (!isComposto[i]) {
                primos.add(i);
            }
        }

        return primos;
    }
}
