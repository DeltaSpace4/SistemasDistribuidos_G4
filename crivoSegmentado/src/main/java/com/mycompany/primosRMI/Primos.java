package com.mycompany.primosRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Primos extends Remote{
    long contarPrimos(long inicio, long fim) throws RemoteException;
    List<Integer> calcularPrimosBase(int limite) throws RemoteException;
}
