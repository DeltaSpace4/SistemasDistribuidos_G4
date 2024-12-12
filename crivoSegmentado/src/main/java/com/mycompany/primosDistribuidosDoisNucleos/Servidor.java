
package com.mycompany.primosDistribuidosDoisNucleos;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
    private static final int PORTA = 58000;
    private static final long INTERVALO_TOTAL = 1_000_000L; // Intervalo: 0 a 10^9
    private static final int NUMERO_CLIENTES = 2;

    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(PORTA)) {
            System.out.println("Servidor aguardando conexões...");
            List<Socket> clientes = new ArrayList<>();

            // Aguarda os clientes conectarem
            for (int i = 0; i < NUMERO_CLIENTES; i++) {
                Socket cliente = servidor.accept();
                clientes.add(cliente);
                System.out.println("Cliente conectado: " + cliente.getInetAddress());
            }

            // Divide o intervalo e distribui para os clientes
            long intervaloPorCliente = INTERVALO_TOTAL / NUMERO_CLIENTES;
            
            for (int i = 0; i < NUMERO_CLIENTES; i++) {
                long inicio = i * intervaloPorCliente;
                long fim = (i == NUMERO_CLIENTES - 1) ? INTERVALO_TOTAL : (inicio + intervaloPorCliente - 1);

                ObjectOutputStream saida = new ObjectOutputStream(clientes.get(i).getOutputStream());
                saida.writeLong(inicio);
                saida.writeLong(fim);
                saida.flush();
            }

            // Recebe os resultados dos clientes
            long totalPrimos = 0;
            for (int i = 0; i < NUMERO_CLIENTES; i++) {
                ObjectInputStream entrada = new ObjectInputStream(clientes.get(i).getInputStream());
                long primos = entrada.readLong();
                System.out.println("Cliente " + (i + 1) + " encontrou " + primos + " primos.");
                totalPrimos += primos;
            }

            System.out.println("Total de primos no intervalo: " + totalPrimos);

            // Fecha as conexões
            for (Socket cliente : clientes) {
                cliente.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
