package com.mycompany.primosdistribuido;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {

    private static final int PORTA = 58000;
    private static final long INTERVALO_MAXIMO = 1_000_000_000L;
    private static final int NUM_CLIENTES = 5;

    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(PORTA)) {
            System.out.println("Servidor aguardando conexões...");

            long intervaloPorCliente = INTERVALO_MAXIMO / NUM_CLIENTES;
            List<Long> resultados = new ArrayList<>();

            // Aguardar conexões de todos os clientes
            List<Socket> clientes = new ArrayList<>();
            for (int i = 0; i < NUM_CLIENTES; i++) {
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress());
                clientes.add(cliente);
            }

            // Distribuir subintervalos para os clientes
            for (int i = 0; i < NUM_CLIENTES; i++) {
                long inicio = i * intervaloPorCliente;
                long fim = (i + 1) * intervaloPorCliente - 1;

                try {
                    ObjectOutputStream saida = new ObjectOutputStream(clientes.get(i).getOutputStream());
                    saida.writeLong(inicio);
                    saida.writeLong(fim);
                    saida.flush(); // Garante que os dados sejam enviados
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

             // Receber resultados de todos os clientes
            for (int i = 0; i < NUM_CLIENTES; i++) {
                try {
                    ObjectInputStream entrada = new ObjectInputStream(clientes.get(i).getInputStream());
                    long primosNoIntervalo = entrada.readLong();
                    resultados.add(primosNoIntervalo);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        clientes.get(i).close(); // Fecha o socket após a interação completa
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            // Exibir resultados
            System.out.println("Resultados recebidos dos clientes:");
            for (int i = 0; i < resultados.size(); i++) {
                System.out.println("Cliente " + (i + 1) + ": " + resultados.get(i) + " primos.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
