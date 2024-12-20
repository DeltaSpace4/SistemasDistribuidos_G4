package com.mycompany.primosRMI;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Servidor extends UnicastRemoteObject implements Primos{
    
    protected Servidor() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
    public long contarPrimos(long inicio, long fim) throws RemoteException{
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
    @Override
    public List<Integer> calcularPrimosBase(int limite) throws RemoteException{
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
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099); //Numero de registro pode interferir na permição de acesso
            Servidor server = new Servidor();
            Naming.rebind("Primos", server);
            System.out.println("Servidor RMI estÃ¡ pronto!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}