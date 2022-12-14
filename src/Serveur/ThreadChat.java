package Serveur;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadChat extends Thread{
    int id;
    BufferedReader in;
    PrintWriter out;
    static PrintWriter[] outs=new PrintWriter[100];
    static int nbid=0;

    public ThreadChat(int id,Socket client) {
        try {
            this.id=id;
            nbid++;
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
            out.println("Id="+id+"\n");
            outs[id]=out;
            /*if (id!=1) {
                System.out.println(id);
                outs[0].println("En attente d'un deuxième joueur...");
            }
            else if(id == 2){
                System.out.println(id);
                outs[0].println("Deuxième joueur arrivé !")
            }*/
        }catch (Exception e) {}
    }

    public void run() {
        try {
            while (true) {
                String message=in.readLine();
                message=id+":"+message;
                System.out.println(message);
                for (int i=0;i<nbid;i++) {
                    if (i!=id)outs[i].println(message);
                }
            }
        }catch (Exception e) {}
    }
}