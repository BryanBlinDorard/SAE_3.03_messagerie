import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    public static void clearTerminal() {
        String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (Exception e) {
                System.out.println("Erreur lors du nettoyage du terminal.");
            }
        } else {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }
    public static void main(String[] args) throws UnknownHostException, IOException {
        try {
            clearTerminal();
            // On se connecte au serveur
            System.out.println("Adresse IP du serveur :");
            Scanner scanner = new Scanner(System.in);
            String ipServeur = scanner.nextLine();
            Socket client = new Socket(ipServeur, 1234); 
            clearTerminal();
            System.out.println("Connexion au serveur reussie !");
            
            // On récupère le nom du client et on vérifie si il est déjà utilisé
            Boolean isNameSet = false;
            String nomClient = "";
            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            while (!isNameSet) {
                System.out.println("Nom du client :");
                nomClient = scanner.nextLine();
                if (nomClient.length() > 0) {
                    out.writeUTF(nomClient);
                    String isNameUsed = in.readUTF();
                    if (isNameUsed.equals("true")) {
                        clearTerminal();
                        System.out.println("Ce nom est déjà utilisé.");
                    } else {
                        isNameSet = true;
                        clearTerminal();
                        System.out.println("Nom du client enregistré.");
                    }
                }
            }

            // On lance un thread qui va écouter les messages du serveur
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