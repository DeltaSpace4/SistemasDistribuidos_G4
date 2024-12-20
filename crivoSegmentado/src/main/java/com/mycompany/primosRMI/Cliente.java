package com.mycompany.primosRMI;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) throws MalformedURLException, NotBoundException {
        try {
        	Primos stub = (Primos)Naming.lookup("rmi://127.0.0.1/Primos");
        	
        	Scanner scanner = new Scanner(System.in);
        	
        	// Entrada do intervalo
            System.out.print("Digite o início do intervalo: ");
            long inicio = scanner.nextLong();

            System.out.print("Digite o fim do intervalo: ");
            long fim = scanner.nextLong();
            
            scanner.close();
        	
            System.out.println("Números primos no intervalo [" + inicio + ", " + fim + "]:");
        	System.out.println("Total de primos: " + stub.contarPrimos(inicio, fim));
        	
        } catch (RemoteException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}