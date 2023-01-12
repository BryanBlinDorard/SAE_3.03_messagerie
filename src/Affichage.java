import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Affichage implements Runnable {
    private DataInputStream in;
    public Affichage(DataInputStream in){
        this.in = in;
    }
    public void run(){
        List<String> motPasAfficher = new ArrayList<>(Arrays.asList("false", "true","insalon","tp","new","clear"));

        
        while(true){
            try {
                // On lit le message envoyé par le serveur
                String message = in.readUTF();
                if (!motPasAfficher.contains(message)) System.out.println(message);
                else {
                    if (message.equals("insalon")) {
                        System.out.println("Vous êtes déjà dans ce salon");
                    } else if (message.equals("tp")) {
                        System.out.println("Vous avez été téléporté dans le salon");
                    } else if (message.equals("new")) {
                        System.out.println("Le salon a été créé");
                    } else if (message.equals("clear")) {
                        Client.clearTerminal();
                    }
                }
            } catch (Exception e) {
                if (e instanceof EOFException || e instanceof SocketException) {
                    System.out.println("Le serveur a fermé la connexion ou vous avez été déconnecté");
                    System.exit(0);
                } else if (e instanceof IOException) {
                    System.out.println("Erreur d'entrée/sortie");
                    System.exit(0);
                } 
            }
        }
    }
}
