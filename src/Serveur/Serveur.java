package Serveur;

import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {
    public static void main(String[] args) {
        try {
            ServerSocket ecoute = new ServerSocket(1500);
            System.out.println("Serveur lancé!");
            int id=0;
            while(true) {
                Socket client = ecoute.accept();
                new ThreadChat(id,client).start();
                id++;
            }
        } catch(Exception e) {
            // Traitement dï¿½erreur
        }

    }
}