package com.mycompany.primosRMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class main extends UnicastRemoteObject {

    PrimosImpl pi;

    public main() throws RemoteException {
        super();
    }

    public static void main(String[] args) {
        try {
            new main().iniciar();
        } catch (RemoteException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void iniciar() {

        try {
            pi = new PrimosImpl();
            Registry registry = LocateRegistry.createRegistry(1099);

            System.out.println("Servidor Geral RMI iniciado...");
            registry.rebind("Primos", pi);
        } catch (RemoteException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
