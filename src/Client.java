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
            System.out.println("Adresse IP du serveur :");
            Scanner scanner = new Scanner(System.in);
            String ipServeur = scanner.nextLine();
            Socket client = new Socket(ipServeur, 1234); 
            System.out.println("Connexion au serveur reussie !");
            
            
            System.out.println("Votre nom :");
            String nomClient = scanner.nextLine();
            
            // On envoie le nom du client au serveur
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeUTF(nomClient);

            // On lance un thread qui va écouter les messages du serveur
            DataInputStream in = new DataInputStream(client.getInputStream());
            Thread t = new Thread(new Affichage(in));
            t.start();
            while (true) {
                String message = scanner.nextLine();
                if (message.length() > 0) {
                    out.writeUTF(message);
                }
            }
        } catch (Exception e) {
            if (e instanceof UnknownHostException) {
                System.out.println("Je ne trouve de serveur avec cette adresse IP.");
            } else if (e instanceof IOException) {
                System.out.println("Erreur de connexion.");
            }
        }
    }
}