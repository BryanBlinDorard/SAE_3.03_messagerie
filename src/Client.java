import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws UnknownHostException, IOException {
        try {
            // On se connecte au serveur
            System.out.println("l'adresse IP du serveur :");
            Scanner sc = new Scanner(System.in);
            String sc1 = sc.nextLine();
            Socket client = new Socket(sc1, 1234); 
            System.out.println("Connexion au serveur reussie");
            
            
            System.out.println("Votre nom :");
            String nomClient = sc.nextLine();
            
            // On envoie le nom du client au serveur
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeUTF(nomClient);

            // On lance un thread qui va écouter les messages du serveur
            DataInputStream in = new DataInputStream(client.getInputStream());
            Thread t = new Thread(new Affichage(in));
            t.start();
            while (true) {
                String message = sc.nextLine();
                out.writeUTF(message);
            }
        } catch (Exception e) {
            if (e instanceof UnknownHostException) {
                System.out.println("Je ne trouve de serveur avec cette adresse IP");
            } else if (e instanceof IOException) {
                System.out.println("Erreur de connexion");
            }
        }
    }
}